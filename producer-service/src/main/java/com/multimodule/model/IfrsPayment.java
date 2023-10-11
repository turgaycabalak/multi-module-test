package com.multimodule.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "ifrs_payments")
public class IfrsPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;
    private String ifrsName;

    public IfrsPayment(String userId, String ifrsName) {
        this.userId = userId;
        this.ifrsName = ifrsName;
    }
}
