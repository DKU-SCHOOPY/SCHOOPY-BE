package com.schoopy.back.user.entity;

import com.schoopy.back.user.dto.request.SignUpRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="user")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;

    @Column(nullable = false)
    private String studentNum;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String department;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String birthDay;
    
    @Column(nullable = false)
    private String gender;
    
    @Column(nullable = false)
    private String phoneNum;
    
    @Column(nullable = false)
    private boolean councilPee;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private int noticeCount;
    


    public UserEntity(SignUpRequestDto dto) {
        this.studentNum = dto.getStudentNum();
        this.name = dto.getName();
        this.email = dto.getStudentNum() + "@dankook.ac.kr";
        this.password = dto.getPassword();
        this.department = dto.getDepartment();
        this.gender = dto.getGender();
        this.birthDay = dto.getBirthDay();
        this.phoneNum = dto.getPhoneNum();
        this.role = "USER";
        this.councilPee = false;
    }
}
