package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.IvaModel;

@Repository
public interface IvaRepository extends CrudRepository<IvaModel, Long>{
    
}
