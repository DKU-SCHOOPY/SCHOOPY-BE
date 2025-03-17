package com.schoopy.back.qrScanning.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventCode;

    @Column(nullable = false, unique = true)
    private String eventName;

    @Column(nullable = false)
    private String qrURL;
}
