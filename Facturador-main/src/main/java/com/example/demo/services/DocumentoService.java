package com.example.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.DocumentoModel;
import com.example.demo.repositories.DocumentoRepository;

@Service
public class DocumentoService {
    @Autowired
    DocumentoRepository documentoRepository;

    public ArrayList<DocumentoModel> obtenerDocumentos() {
        return (ArrayList<DocumentoModel>) documentoRepository.findAll();
    }

    public DocumentoModel guardarDocumento(DocumentoModel documento) {
        return documentoRepository.save(documento);
    }

    public boolean eliminarDocumento(Long id) {
        try {
            documentoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<DocumentoModel> obtenerPorId(Long id) {
        return documentoRepository.findById(id);
    }

}
