package com.example.demo.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.FacturaModel;

@Repository
public interface FacturaRepoitory extends CrudRepository<FacturaModel, Long>{
    @Query("SELECT f FROM FacturaModel f WHERE f.cliente.cedula = :cedula")
    ArrayList<FacturaModel> findByClienteCedula(@Param("cedula") String cedula);
    
}
