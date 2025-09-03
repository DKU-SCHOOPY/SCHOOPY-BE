package com.schoopy.back.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="president")
public class PresidentEntity {
    @Id
    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String studentNum;
}   
