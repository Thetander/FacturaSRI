package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.DocumentoModel;
import com.example.demo.services.DocumentoService;

@RestController
@RequestMapping("/documento")
public class DocumentoController {
    @Autowired
    DocumentoService documentoService;

    @GetMapping()
    public ArrayList<DocumentoModel> obtenerDocumentos() {
        return documentoService.obtenerDocumentos();
    }

    @PostMapping()
    public DocumentoModel guardarDocumento(@RequestBody DocumentoModel documento) {
        return this.documentoService.guardarDocumento(documento);
    }

    @GetMapping(path = "/{id}")
    public Optional<DocumentoModel> obtenerDocumentoPorId(@PathVariable("id") Long id) {
        return this.documentoService.obtenerPorId(id);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminarDocumentoPorId(@PathVariable("id") Long id) {
        boolean ok = this.documentoService.eliminarDocumento(id);
        if (ok) {
            return "Se eliminó el documento con id " + id;
        } else {
            return "No se pudo eliminar el documento con id " + id;
        }
    }
    
}
