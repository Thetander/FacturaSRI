package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.EmpresaModel;

@Repository
public interface EmpresaRepository extends CrudRepository<EmpresaModel, Long>{
    
}
