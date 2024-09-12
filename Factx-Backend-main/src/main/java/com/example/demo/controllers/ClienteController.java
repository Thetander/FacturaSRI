package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.ClienteModel;
import com.example.demo.services.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @GetMapping()
    public ArrayList<ClienteModel> obtenerClientes() {
        return clienteService.obteberClientes();
    }

    @PostMapping()
    public ClienteModel guardarCliente(@RequestBody ClienteModel cliente) {
        return this.clienteService.guardarCliente(cliente);
    }

    @GetMapping(path = "/{id}")
    public Optional<ClienteModel>obtenerClientePorId(@PathVariable("id") Long id) {
        return this.clienteService.obtenerPorId(id);
    }

    @GetMapping("/query")
    public ArrayList<ClienteModel> obtenerClientePorNombre(@RequestParam("nombre") String nombre) {
        return this.clienteService.obtenerPorNombre(nombre);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminarClientePorId(@PathVariable("id") Long id) {
        boolean ok = this.clienteService.eliminarCliente(id);
        if (ok) {
            return "Se eliminó el cliente con id " + id;
        } else {
            return "No se pudo eliminar el cliente con id " + id;
        }
    }
}
