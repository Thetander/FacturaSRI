package com.example.demo.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.UsuarioModel;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioModel, Long> {
    @Query("SELECT u FROM UsuarioModel u WHERE u.usuario = :usuario AND u.contrasena = :contrasena")
    ArrayList<UsuarioModel> findByUsuarioAndContrasena(String usuario, String contrasena);
}
