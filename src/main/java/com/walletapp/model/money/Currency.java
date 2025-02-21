package com.walletapp.model.money;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "type"))
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CurrencyType type;

    private double conversionFactor;

    public Currency() {}
    public Currency(CurrencyType type, double conversionFactor) {
        this.type = type;
        this.conversionFactor = conversionFactor;
    }

    public Long getId() {
        return id;
    }

    public CurrencyType getType() {
        return type;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }
}