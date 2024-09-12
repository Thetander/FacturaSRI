package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_cliente")
public class TipoClienteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_tipo_cliente;
    @Column(unique = true, nullable = false)
    private String tipo;

    public Long getId_tipo_cliente() {
        return id_tipo_cliente;
    }

    public void setId_tipo_cliente(Long id_tipo_cliente) {
        this.id_tipo_cliente = id_tipo_cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
