package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class ClienteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_cliente;
    @ManyToOne
    @JoinColumn(name = "id_empresa_per", referencedColumnName = "id_empresa")
    private EmpresaModel empresa;
    @ManyToOne
    @JoinColumn(name = "id_tipo_cliente_per", referencedColumnName = "id_tipo_cliente")
    private TipoClienteModel tipo_cliente;
    @Column(unique = true, nullable = false)
    private String cedula;
    @Column(nullable = false)
    private String nombre;
    private String apellido;
    @Column(nullable = false)
    private String correo;
    @Column(nullable = false)
    private String direccion;
    @Column(nullable = false)
    private String telefono;

    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public EmpresaModel getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaModel empresa) {
        this.empresa = empresa;
    }

    public TipoClienteModel getTipo_cliente() {
        return tipo_cliente;
    }

    public void setTipo_cliente(TipoClienteModel tipo_cliente) {
        this.tipo_cliente = tipo_cliente;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
}
