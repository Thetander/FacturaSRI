package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.EmpresaModel;
import com.example.demo.services.EmpresaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
    @Autowired
    EmpresaService empresaService;

    @GetMapping()
    public ArrayList<EmpresaModel> obtenerEmpresas() {
        return empresaService.obtenerEmpresas();
    }

    @PostMapping()
    public EmpresaModel guardarEmpresa(@RequestParam("empresa") String empresaJSON,
            @RequestParam("logo") MultipartFile logo, @RequestParam("firma_electronica") MultipartFile firmaElectronica)
            throws IOException {

        EmpresaModel empresa = new ObjectMapper().readValue(empresaJSON, EmpresaModel.class);

        String logoPath = saveFile(logo, "logos", empresa.getRuc());
        String firmaElectronicaPath = saveFile(firmaElectronica, "firmas", empresa.getRuc());

        empresa.setLogo(logoPath);
        empresa.setFirma_electronica(firmaElectronicaPath);

        return this.empresaService.guardarEmpresa(empresa);
    }

    @GetMapping(path = "/{id}")
    public Optional<EmpresaModel> obtenerEmpresaPorId(@PathVariable("id") Long id) {
        return this.empresaService.obtenerPorId(id);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminarEmpresaPorId(@PathVariable("id") Long id) {
        boolean ok = this.empresaService.eliminarEmpresa(id);
        if (ok) {
            return "Se eliminó la empresa con id " + id;
        } else {
            return "No se pudo eliminar la empresa con id " + id;
        }
    }

    private String saveFile(MultipartFile file, String directory, String ruc) throws IOException {
        // Directorio donde se guardarán los archivos
        String uploadDir = System.getProperty("user.dir") + "/uploads/" + directory;

        // Crear directorio si no existe
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Guardar archivo
        String filePath = uploadDir + "/" + ruc + "-" + new Date(System.currentTimeMillis()) + "-"
                + file.getOriginalFilename();
        File dest = new File(filePath);
        file.transferTo(dest);

        // Retornar la ruta relativa
        return "/uploads/" + directory + "/" + ruc + "-" + new Date(System.currentTimeMillis()) + "-"
                + file.getOriginalFilename();
    }

}
