package edu.uade.cookingrecipes.entity;

import edu.uade.cookingrecipes.model.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account_movements")
public class AccountMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LocalDateTime", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime dateTime;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "reason", nullable = false)
    private String reason;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;
}
