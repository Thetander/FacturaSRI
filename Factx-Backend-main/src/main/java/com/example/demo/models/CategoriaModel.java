package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "categoria")
public class CategoriaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_categoria;
    @ManyToOne
    @JoinColumn(name = "id_iva_per", referencedColumnName = "id_iva")
    private IvaModel iva;
    @ManyToOne
    @JoinColumn(name = "id_empresa_per", referencedColumnName = "id_empresa")
    private EmpresaModel empresa;
    @Column(unique = true, nullable = false)
    private String categoria;

    public Long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public IvaModel getIva() {
        return iva;
    }

    public void setIva(IvaModel iva) {
        this.iva = iva;
    }

    public EmpresaModel getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaModel empresa) {
        this.empresa = empresa;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
