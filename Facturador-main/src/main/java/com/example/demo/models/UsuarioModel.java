package com.example.demo.models;

import jakarta.persistence.*;

@Entity // This annotation is used to specify that the class is an entity and is mapped
        // to a database table.
@Table(name = "usuario") // This annotation is used to specify the table name that the class represents.
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_usuario;
    @ManyToOne
    @JoinColumn(name = "id_empresa_per", referencedColumnName = "id_empresa")
    private EmpresaModel empresa;

    @ManyToOne
    @JoinColumn(name = "id_rol_per", referencedColumnName = "id_rol")
    private RolModel rol;

    @Column(unique = true, nullable = false)
    private String usuario;
    @Column(nullable = false)
    private String contrasena;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public EmpresaModel getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaModel empresa) {
        this.empresa = empresa;
    }

    public RolModel getRol() {
        return rol;
    }

    public void setRol(RolModel rol) {
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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

}
