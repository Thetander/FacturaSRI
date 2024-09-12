package com.example.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.CategoriaModel;
import com.example.demo.repositories.CategoriaRepository;

@Service
public class CategoriaService {
    @Autowired
    CategoriaRepository categoriaRepository;

    public ArrayList<CategoriaModel> obteberCategorias() {
        return (ArrayList<CategoriaModel>) categoriaRepository.findAll();
    }

    public CategoriaModel guardarCategoria(CategoriaModel categoria) {
        return categoriaRepository.save(categoria);
    }

    public Optional<CategoriaModel> obtenerPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public boolean eliminarCategoria(Long id) {
        try {
            categoriaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
