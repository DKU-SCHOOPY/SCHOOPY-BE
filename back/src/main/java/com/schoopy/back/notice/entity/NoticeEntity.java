package com.schoopy.back.notice.entity;

import com.schoopy.back.user.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="notice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="notice")
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long noticeId;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false) 
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private UserEntity receiver;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private boolean isPresident;

    @Column(nullable = false)
    private boolean readCheck;



    public NoticeEntity(UserEntity sender, UserEntity receiver, String title, String message, boolean isPresident) {
        this.sender = sender;
        this.receiver = receiver;
        this.title = title;
        this.message = message;
        this.readCheck = false;
        this.isPresident = isPresident;
    }
}
