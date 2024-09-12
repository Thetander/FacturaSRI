package com.example.demo.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.DetalleFacturaModel;

@Repository
public interface DetalleFacturaRepository extends CrudRepository<DetalleFacturaModel, Long>{
    public abstract ArrayList<DetalleFacturaModel> findByFactura_IdFactura(Long id);
    
}
