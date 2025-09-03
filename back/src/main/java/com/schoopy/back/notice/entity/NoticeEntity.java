package com.schoopy.back.notice.entity;

import com.schoopy.back.user.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(nullable = false)
    private UserEntity sender;

    @Column(nullable = false)
    private UserEntity reciever;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private boolean isPresident;

    @Column(nullable = false)
    private boolean readCheck;



    public NoticeEntity(UserEntity sender, UserEntity reciever, String title, String message, boolean isPresident) {
        this.sender = sender;
        this.reciever = reciever;
        this.title = title;
        this.message = message;
        this.readCheck = false;
    }
}
