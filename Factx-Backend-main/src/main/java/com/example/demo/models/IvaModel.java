package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "iva")
public class IvaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_iva;
    @Column(unique = true, nullable = false)
    private String iva_nombre;
    @Column(unique = true, nullable = false)
    private Float iva;

    public Long getId_iva() {
        return id_iva;
    }

    public void setId_iva(Long id_iva) {
        this.id_iva = id_iva;
    }

    public String getIva_nombre() {
        return iva_nombre;
    }

    public void setIva_nombre(String iva_nombre) {
        this.iva_nombre = iva_nombre;
    }

    public Float getIva() {
        return iva;
    }

    public void setIva(Float iva) {
        this.iva = iva;
    }
}
