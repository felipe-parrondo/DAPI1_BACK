package edu.uade.cookingrecipes.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipe_list")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;
    @Column(name = "password", length = 100, nullable = false)
    private String password;
}
