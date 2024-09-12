package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.DocumentoModel;

@Repository
public interface DocumentoRepository extends CrudRepository<DocumentoModel, Long> {

}
