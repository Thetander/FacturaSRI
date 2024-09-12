package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "forma_pago")
public class FormaPagoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_forma_pago;
    @Column(unique = true, nullable = false)
    private String forma_pago;

    public Long getId_forma_pago() {
        return id_forma_pago;
    }

    public void setId_forma_pago(Long id_forma_pago) {
        this.id_forma_pago = id_forma_pago;
    }

    public String getForma_pago() {
        return forma_pago;
    }

    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }
}
