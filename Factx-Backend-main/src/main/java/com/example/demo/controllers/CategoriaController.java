package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.CategoriaModel;
import com.example.demo.services.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;

    @GetMapping()
    public ArrayList<CategoriaModel> obtenerCategorias() {
        return categoriaService.obteberCategorias();
    }

    @PostMapping()
    public CategoriaModel guardarCategoria(@RequestBody CategoriaModel categoria) {
        return this.categoriaService.guardarCategoria(categoria);
    }

    @GetMapping(path = "/{id}")
    public Optional<CategoriaModel> obtenerCategoriaPorId(@PathVariable("id") Long id) {
        return this.categoriaService.obtenerPorId(id);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminarCategoriaPorId(@PathVariable("id") Long id) {
        boolean ok = this.categoriaService.eliminarCategoria(id);
        if (ok) {
            return "Se eliminó la categoria con id " + id;
        } else {
            return "No se pudo eliminar la categoria con id " + id;
        }
    }

}
