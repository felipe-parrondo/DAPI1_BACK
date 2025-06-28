package edu.uade.cookingrecipes.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String name;
    @Column(name = "address", length = 255, nullable = false)
    private String address;
    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;
    @Column(name = "email", length = 100, nullable = false)
    private String email;
    @ElementCollection
    @Column(name = "photo_url", length = 255, nullable = true)
    private List<String> photoUrl;
    @Column(name = "location", length = 255, nullable = false)
    private String location;
}
