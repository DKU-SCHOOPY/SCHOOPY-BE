package com.schoopy.back.event.entity;

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

    private Boolean isStudent;
    private Boolean councilFeePaid;
    private Boolean isPaymentCompleted;
}
