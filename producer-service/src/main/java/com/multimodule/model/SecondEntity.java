package com.multimodule.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "seconds")
public class SecondEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    public SecondEntity(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
