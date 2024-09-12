package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.TipoClienteModel;

@Repository
public interface TipoClienteRepository extends CrudRepository<TipoClienteModel, Long> {

}
