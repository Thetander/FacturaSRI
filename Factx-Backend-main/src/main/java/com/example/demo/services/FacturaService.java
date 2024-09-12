package com.example.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.FacturaModel;
import com.example.demo.repositories.FacturaRepoitory;

@Service
public class FacturaService {
    @Autowired
    FacturaRepoitory facturaRepoitory;

    public ArrayList<FacturaModel> obteberFacturas() {
        return (ArrayList<FacturaModel>) facturaRepoitory.findAll();
    }

    public FacturaModel guardarFactura(FacturaModel factura) {
        return facturaRepoitory.save(factura);
    }

    public Optional<FacturaModel> obtenerPorId(Long id) {
        return facturaRepoitory.findById(id);
    }

    public ArrayList<FacturaModel> obtenerPorCedulaCliente(String cedula) {
        return facturaRepoitory.findByClienteCedula(cedula);
    }

    public boolean eliminarFactura(Long id) {
        try {
            facturaRepoitory.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
