package com.schoopy.back.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity(name="certification")
@Table(name="certification")
public class CertificationEntity {
    @Id
    private String email;
    private String certificationNumber;
}
