package com.schoopy.back.chat.service.implement;

import com.schoopy.back.chat.dto.request.ChatMessageRequestDto;
import com.schoopy.back.chat.dto.response.ChatMessageResponseDto;
import com.schoopy.back.chat.dto.response.ChatRoomListItemResponseDto;
import com.schoopy.back.chat.dto.response.CouncilContactListResponseDto;
import com.schoopy.back.chat.dto.response.CouncilContactResponseDto;
import com.schoopy.back.chat.entity.ChatMessageEntity;
import com.schoopy.back.chat.entity.ChatRoomEntity;
import com.schoopy.back.chat.repository.ChatMessageRepository;
import com.schoopy.back.chat.repository.ChatRoomRepository;
import com.schoopy.back.chat.service.ChatService;
import com.schoopy.back.user.entity.PresidentEntity;
import com.schoopy.back.user.entity.UserEntity;
import com.schoopy.back.user.repository.PresidentRepository;
import com.schoopy.back.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImplement implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final PresidentRepository presidentRepository;

    @Override
    @Transactional
    public ResponseEntity<? super ChatMessageResponseDto> saveMessage(ChatMessageRequestDto dto) {
        String senderId = dto.getSenderId();
        String receiverId = dto.getReceiverId();

        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("자기 자신에게 메시지를 보낼 수 없습니다.");
        }

        // 문자열 기준 정렬(학생번호가 동일 자리수라면 lexicographical == numerical)
        String userA = senderId.compareTo(receiverId) < 0 ? senderId : receiverId;
        String userB = senderId.compareTo(receiverId) < 0 ? receiverId : senderId;

        ChatRoomEntity room = chatRoomRepository.findByUserAAndUserB(userA, userB)
                .orElseGet(() -> {
                    ChatRoomEntity newRoom = ChatRoomEntity.builder()
                            .userA(userA)
                            .userB(userB)
                            .build();
                    return chatRoomRepository.save(newRoom);
                });

        ChatMessageEntity message = ChatMessageEntity.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .content(dto.getMessage())
                .room(room)
                .build();

        chatMessageRepository.save(message);
        return ResponseEntity.ok(new ChatMessageResponseDto(message));
    }

    @Override
    public ChatRoomEntity getOrCreateRoom(String senderId, String receiverId) {
        String userA = senderId.compareTo(receiverId) < 0 ? senderId : receiverId;
        String userB = senderId.compareTo(receiverId) < 0 ? receiverId : senderId;

        return chatRoomRepository.findByUserAAndUserB(userA, userB)
                .orElseGet(() -> chatRoomRepository.save(
                        ChatRoomEntity.builder().userA(userA).userB(userB).build()
                ));
    }

    @Override
    public ResponseEntity<? super List<ChatMessageResponseDto>> getMessagesByRoomId(Long roomId) {
        List<ChatMessageEntity> messages = chatMessageRepository.findByRoomIdOrderByCreatedAtAsc(roomId);
        List<ChatMessageResponseDto> response = messages.stream()
                .map(ChatMessageResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<? super List<ChatRoomListItemResponseDto>> getChatRoomsByUserId(String myId) {
        // 내가 속한 방들
        List<ChatRoomEntity> rooms = chatRoomRepository.findByUserAOrUserB(myId, myId);
        if (rooms.isEmpty()) return ResponseEntity.ok(List.of());

        // 상대 학번 집합
        Set<String> counterpartIds = rooms.stream()
                .map(r -> r.getUserA().equals(myId) ? r.getUserB() : r.getUserA())
                .collect(Collectors.toSet());

        // 한 번에 사용자 조회 -> Map<학번, 이름>
        Map<String, String> nameById = userRepository.findByStudentNumIn(counterpartIds).stream()
                .collect(Collectors.toMap(UserEntity::getStudentNum, UserEntity::getName));

        // DTO 구성 (최근 메시지 포함)
        List<ChatRoomListItemResponseDto> list = rooms.stream().map(room -> {
            String counterpartId = room.getUserA().equals(myId) ? room.getUserB() : room.getUserA();

            ChatMessageEntity last = chatMessageRepository
                    .findTop1ByRoom_IdOrderByCreatedAtDesc(room.getId());

            return new ChatRoomListItemResponseDto(
                    room.getId(),
                    counterpartId,
                    nameById.getOrDefault(counterpartId, "(탈퇴한 사용자)"),
                    last != null ? last.getContent() : null,
                    last != null ? last.getCreatedAt() : null
            );
        }).collect(Collectors.toList());

        // 최근 대화순 정렬(옵션)
        list.sort(Comparator.comparing(
                ChatRoomListItemResponseDto::getLastMessageAt,
                Comparator.nullsLast(Comparator.naturalOrder())
        ).reversed());

        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<? super CouncilContactListResponseDto> getCouncilContactsForStudent(String studentNum) {
        UserEntity student = userRepository.findByStudentNum(studentNum);
        if (student == null) return ResponseEntity.notFound().build();

        // 중복 방지 + 입력 순서 보존
        var contacts = new java.util.LinkedHashMap<String, CouncilContactResponseDto>();

        PresidentEntity deptPresident = presidentRepository.findByDepartment(student.getDepartment());
        if (deptPresident != null) {
            UserEntity u = userRepository.findByStudentNum(deptPresident.getStudentNum());
            if (u != null) {
                contacts.put(u.getStudentNum(),
                    CouncilContactResponseDto.builder()
                        .presidentStudentNum(u.getStudentNum())
                        .presidentName(u.getName())
                        .department(deptPresident.getDepartment()) // ← 부서 세팅
                        .build()
                );
            }
        }

        String fixedStudentNum = "32203027";
        UserEntity fixedUser = userRepository.findByStudentNum(fixedStudentNum);
        if (fixedUser != null) {
            String fixedDept = null;
            try {
                PresidentEntity fixedPres = presidentRepository.findByStudentNum(fixedStudentNum);
                if (fixedPres != null) fixedDept = fixedPres.getDepartment();
            } catch (Exception ignored) {}

            if (fixedDept == null) {
                // fallback: 테이블에 없다면 고정 문자열
                fixedDept = "SW융합대학학생회";
            }

            contacts.put(fixedUser.getStudentNum(),
                CouncilContactResponseDto.builder()
                    .presidentStudentNum(fixedUser.getStudentNum())
                    .presidentName(fixedUser.getName())
                    .department(fixedDept) // ← 부서 세팅
                    .build()
            );
        }

        if (contacts.isEmpty()) return ResponseEntity.notFound().build();

        var body = CouncilContactListResponseDto.builder()
                .contacts(new java.util.ArrayList<>(contacts.values()))
                .build();

        return ResponseEntity.ok(body);
    }
}