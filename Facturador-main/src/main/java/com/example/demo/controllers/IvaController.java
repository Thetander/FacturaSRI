package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.IvaModel;
import com.example.demo.services.IvaService;

@RestController
@RequestMapping("/iva")
public class IvaController {
    @Autowired
    IvaService ivaService;

    @GetMapping()
    public ArrayList<IvaModel> obtenerIvas() {
        return ivaService.obteberIvas();
    }

    @PostMapping()
    public IvaModel guardarIva(@RequestBody IvaModel iva) {
        return this.ivaService.guardarIva(iva);
    }

    @GetMapping(path = "/{id}")
    public Optional<IvaModel> obtenerIvaPorId(@PathVariable("id") Long id) {
        return this.ivaService.obtenerPorId(id);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminarIvaPorId(@PathVariable("id") Long id) {
        boolean ok = this.ivaService.eliminarIva(id);
        if (ok) {
            return "Se eliminó el iva con id " + id;
        } else {
            return "No se pudo eliminar el iva con id " + id;
        }
    }

}
