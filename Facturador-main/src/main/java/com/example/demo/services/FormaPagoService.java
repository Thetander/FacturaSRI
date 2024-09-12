package com.example.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.FormaPagoModel;
import com.example.demo.repositories.FormaPagoRepository;

@Service
public class FormaPagoService {
    @Autowired
    FormaPagoRepository formaPagoRepository;

    public ArrayList<FormaPagoModel> obteberFormasPago() {
        return (ArrayList<FormaPagoModel>) formaPagoRepository.findAll();
    }

    public FormaPagoModel guardarFormaPago(FormaPagoModel formaPago) {
        return formaPagoRepository.save(formaPago);
    }

    public Optional<FormaPagoModel> obtenerPorId(Long id) {
        return formaPagoRepository.findById(id);
    }

    public boolean eliminarFormaPago(Long id) {
        try {
            formaPagoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
