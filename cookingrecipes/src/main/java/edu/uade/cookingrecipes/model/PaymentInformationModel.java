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

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_information")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PaymentInformationModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(name = "is_credit", nullable = false)
    private Boolean isCredit;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "cvv", nullable = false)
    private String cvv;

    @Column(name = "expiration_date", nullable = false)
    private String expirationDate;

    @Column(name = "id_number", nullable = false)
    private String idNumber;
}
