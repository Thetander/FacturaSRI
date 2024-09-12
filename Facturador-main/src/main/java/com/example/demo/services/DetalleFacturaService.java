package com.example.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.DetalleFacturaModel;
import com.example.demo.repositories.DetalleFacturaRepository;

@Service
public class DetalleFacturaService {
    @Autowired
    DetalleFacturaRepository detalleFacturaRepository;

    public ArrayList<DetalleFacturaModel> obteberDetalleFacturas() {
        return (ArrayList<DetalleFacturaModel>) detalleFacturaRepository.findAll();
    }

    public DetalleFacturaModel guardarDetalleFactura(DetalleFacturaModel detalleFactura) {
        return detalleFacturaRepository.save(detalleFactura);
    }

    public Optional<DetalleFacturaModel> obtenerPorId(Long id) {
        return detalleFacturaRepository.findById(id);
    }

    public ArrayList<DetalleFacturaModel> obtenerPorFacturaId(Long id) {
        return detalleFacturaRepository.findByFactura_IdFactura(id);
    }

    public boolean eliminarDetalleFactura(Long id) {
        try {
            detalleFacturaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
