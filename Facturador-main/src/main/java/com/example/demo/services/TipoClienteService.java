package com.example.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.TipoClienteModel;
import com.example.demo.repositories.TipoClienteRepository;

@Service
public class TipoClienteService {
    @Autowired
    TipoClienteRepository tipoClienteRepository;

    public ArrayList<TipoClienteModel> obteberTiposCliente() {
        return (ArrayList<TipoClienteModel>) tipoClienteRepository.findAll();
    }

    public TipoClienteModel guardarTipoCliente(TipoClienteModel tipoCliente) {
        return tipoClienteRepository.save(tipoCliente);
    }

    public Optional<TipoClienteModel> obtenerPorId(Long id) {
        return tipoClienteRepository.findById(id);
    }

    public boolean eliminarTipoCliente(Long id) {
        try {
            tipoClienteRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
