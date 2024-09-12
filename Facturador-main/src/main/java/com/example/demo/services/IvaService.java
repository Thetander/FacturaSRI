package com.example.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.IvaModel;
import com.example.demo.repositories.IvaRepository;

@Service
public class IvaService {
    @Autowired
    IvaRepository ivaRepository;

    public ArrayList<IvaModel> obteberIvas() {
        return (ArrayList<IvaModel>) ivaRepository.findAll();
    }

    public IvaModel guardarIva(IvaModel iva) {
        return ivaRepository.save(iva);
    }

    public Optional<IvaModel> obtenerPorId(Long id) {
        return ivaRepository.findById(id);
    }

    public boolean eliminarIva(Long id) {
        try {
            ivaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
