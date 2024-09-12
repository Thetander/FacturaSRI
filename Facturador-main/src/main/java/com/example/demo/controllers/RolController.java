package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.RolModel;
import com.example.demo.services.RolService;

@RestController
@RequestMapping("/rol")
public class RolController {
    @Autowired
    RolService rolService;

    @GetMapping()
    public ArrayList<RolModel> obtenerRoles() {
        return rolService.obteberRoles();
    }

    @PostMapping()
    public RolModel guardarRol(@RequestBody RolModel rol) {
        return this.rolService.guardarRol(rol);
    }

    @GetMapping(path = "/{id}")
    public Optional<RolModel> obtenerRolPorId(@PathVariable("id") Long id) {
        return this.rolService.obtenerPorId(id);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminarRolPorId(@PathVariable("id") Long id) {
        boolean ok = this.rolService.eliminarRol(id);
        if (ok) {
            return "Se eliminó el rol con id " + id;
        } else {
            return "No se pudo eliminar el rol con id " + id;
        }
    }
}
