package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "empresa")
public class EmpresaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_empresa;
    @Column(unique = true, nullable = false)
    private String ruc;
    @Column(nullable = false)
    private String razon_social;
    @Column(nullable = false)
    private String nombre_comercial;
    @Column(nullable = false)
    private String direccion;
    @Column(nullable = false)
    private String telefono;
    @Column(nullable = false)
    private String logo;
    @Column(nullable = false)
    private String tipo_contribuyente;
    @Column(nullable = false)
    private Boolean lleva_contabilidad;
    @Column(nullable = false)
    private String firma_electronica;
    @Column(nullable = false)
    private String contrasena_firma_electronica;
    @Column(nullable = false)
    private Boolean desarrollo;

    public Long getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(Long id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getNombre_comercial() {
        return nombre_comercial;
    }

    public void setNombre_comercial(String nombre_comercial) {
        this.nombre_comercial = nombre_comercial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTipo_contribuyente() {
        return tipo_contribuyente;
    }

    public void setTipo_contribuyente(String tipo_contribuyente) {
        this.tipo_contribuyente = tipo_contribuyente;
    }

    public Boolean getLleva_contabilidad() {
        return lleva_contabilidad;
    }

    public void setLleva_contabilidad(Boolean lleva_contabilidad) {
        this.lleva_contabilidad = lleva_contabilidad;
    }

    public String getFirma_electronica() {
        return firma_electronica;
    }

    public void setFirma_electronica(String firma_electronica) {
        this.firma_electronica = firma_electronica;
    }

    public String getContrasena_firma_electronica() {
        return contrasena_firma_electronica;
    }

    public void setContrasena_firma_electronica(String contrasena_firma_electronica) {
        this.contrasena_firma_electronica = contrasena_firma_electronica;
    }

    public Boolean getDesarrollo() {
        return desarrollo;
    }

    public void setDesarrollo(Boolean desarrollo) {
        this.desarrollo = desarrollo;
    }

}
