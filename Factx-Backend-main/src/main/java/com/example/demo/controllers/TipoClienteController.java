package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.TipoClienteModel;
import com.example.demo.services.TipoClienteService;

@RestController
@RequestMapping("/tipoCliente")
public class TipoClienteController {
    @Autowired
    TipoClienteService tipoClienteService;

    @GetMapping()
    public ArrayList<TipoClienteModel> obtenerTiposCliente() {
        return tipoClienteService.obteberTiposCliente();
    }

    @PostMapping()
    public TipoClienteModel guardarTipoCliente(@RequestBody TipoClienteModel tipoCliente) {
        return this.tipoClienteService.guardarTipoCliente(tipoCliente);
    }

    @GetMapping(path = "/{id}")
    public Optional<TipoClienteModel> obtenerTipoClientePorId(@PathVariable("id") Long id) {
        return this.tipoClienteService.obtenerPorId(id);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminarTipoClientePorId(@PathVariable("id") Long id) {
        boolean ok = this.tipoClienteService.eliminarTipoCliente(id);
        if (ok) {
            return "Se eliminó el tipo de cliente con id " + id;
        } else {
            return "No se pudo eliminar el tipo de cliente con id " + id;
        }
    }
}
