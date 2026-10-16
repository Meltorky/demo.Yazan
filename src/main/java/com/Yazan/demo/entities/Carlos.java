package com.Yazan.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "carlos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carlos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 61)
    private String name;

    @Pattern(regexp = "^01\\d{9}$", message = "Phone number must start with '01' and be exactly 11 digits long.")
    @Column(name = "phone_number", nullable = false, unique = true, length = 11)
    private String phoneNumber;

    @Min(value = 0)
    @Max(value = 5)
    private int stamps;

    @Min(value = 1)
    private int level = 1;

    @UuidGenerator
    @Column(unique = true, nullable = false)
    private String qrCodeIdentifier;
}
