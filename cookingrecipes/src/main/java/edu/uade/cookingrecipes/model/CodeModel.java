package edu.uade.cookingrecipes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authentication_codes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class CodeModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeId;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "expiration", nullable = false)
    private LocalDateTime expiration;
}
