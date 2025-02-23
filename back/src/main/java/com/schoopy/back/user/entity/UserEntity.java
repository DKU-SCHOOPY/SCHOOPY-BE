package com.schoopy.back.user.entity;

import com.schoopy.back.user.dto.request.SignUpRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class UserEntity {
    @Id
    private String email;
    private String password;
    private String role;

    public UserEntity(SignUpRequestDto dto) {
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.role = "USER";
    }
}
