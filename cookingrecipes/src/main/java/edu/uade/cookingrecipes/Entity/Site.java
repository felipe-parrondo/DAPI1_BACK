package edu.uade.cookingrecipes.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "site")
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 100, nullable = false)
    private String name; // Nombre del sitio
    @Column(name = "address", length = 255, nullable = false)
    private String address; // Dirección del sitio
    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber; // Número de teléfono del sitio
    @Column(name = "email", length = 100, nullable = false)
    private String email; // Correo electrónico del sitio
    @Column(name = "description", length = 500)
    private String description; // Descripción del sitio
}
