package com.schoopy.back.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="certification")
public class CertificationEntity {
    @Id
    @Column(nullable = false, updatable = false)
    private String studentNum;

    @Column(nullable = false)
    private String certificationNumber;
}
