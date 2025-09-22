package com.schoopy.back.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouncilContactListResponseDto {
    private List<CouncilContactResponseDto> contacts;
}
