package com.example.demo.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.ClienteModel;

@Repository
public interface ClienteRepository extends CrudRepository<ClienteModel, Long> {
    @Query("SELECT c FROM ClienteModel c WHERE c.nombre LIKE %:cliente% OR c.apellido LIKE %:cliente%")
    public ArrayList<ClienteModel> findByNombreOrApellidoContaining(@Param("cliente") String cliente);

}
