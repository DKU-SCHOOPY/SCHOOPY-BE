package com.schoopy.back.event.entity;

import java.util.ArrayList;
import java.util.List;

import com.schoopy.back.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="submit_survey")
public class ApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name="event_code")
    private EventEntity eventCode;

    @ManyToOne
    @JoinColumn(name="student_num")
    private UserEntity user;

    @Column(nullable = false)
    private Boolean isPaymentCompleted=false;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerEntity> answers = new ArrayList<>();
}