package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.FormaPagoModel;
import com.example.demo.services.FormaPagoService;

@RestController
@RequestMapping("/formaPago")
public class FormaPagoController {
    @Autowired
    FormaPagoService formaPagoService;

    @GetMapping()
    public ArrayList<FormaPagoModel> obtenerFormasPago() {
        return formaPagoService.obteberFormasPago();
    }

    @PostMapping()
    public FormaPagoModel guardarFormaPago(@RequestBody FormaPagoModel formaPago) {
        return this.formaPagoService.guardarFormaPago(formaPago);
    }

    @GetMapping(path = "/{id}")
    public Optional<FormaPagoModel> obtenerFormaPagoPorId(@PathVariable("id") Long id) {
        return this.formaPagoService.obtenerPorId(id);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminarFormaPagoPorId(@PathVariable("id") Long id) {
        boolean ok = this.formaPagoService.eliminarFormaPago(id);
        if (ok) {
            return "Se eliminó la forma de pago con id " + id;
        } else {
            return "No se pudo eliminar la forma de pago con id " + id;
        }
    }

}
