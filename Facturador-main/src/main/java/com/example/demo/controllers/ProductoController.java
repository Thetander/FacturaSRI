package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.ProductoModel;
import com.example.demo.services.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    ProductoService productoService;

    @GetMapping()
    public ArrayList<ProductoModel> obtenerProductos() {
        return productoService.obtenerProductos();
    }

    @PostMapping()
    public ProductoModel guardarProducto(@RequestParam("producto") String productoJSON,
            @RequestParam("icono") MultipartFile icono) throws IOException {
        ProductoModel producto = new ObjectMapper().readValue(productoJSON, ProductoModel.class);

        String iconoPath = saveFile(icono, "iconos", producto.getProducto());

        producto.setIcono(iconoPath);

        return this.productoService.guardarProducto(producto);
    }

    @GetMapping(path = "/{id}")
    public Optional<ProductoModel> obtenerProductoPorId(@PathVariable("id") Long id) {
        return this.productoService.obtenerPorId(id);
    }

    @GetMapping(path = "/query")
    public ArrayList<ProductoModel> obtenerProductoPorNombre(@RequestParam("nombre") String nombre) {
        return this.productoService.obtenerPorNombre(nombre);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminarProductoPorId(@PathVariable("id") Long id) {
        boolean ok = this.productoService.eliminarProducto(id);
        if (ok) {
            return "Se eliminó el producto con id " + id;
        } else {
            return "No se pudo eliminar el producto con id " + id;
        }
    }

    private String saveFile(MultipartFile file, String directory, String producto) throws IOException {
        String uploadDir = System.getProperty("user.dir") + "/uploads/" + directory;

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String filePath = uploadDir + "/" + producto + "-" + new Date(System.currentTimeMillis()) + "-"
                + file.getOriginalFilename();
        File dest = new File(filePath);
        file.transferTo(dest);

        return "/uploads/" + directory + "/" + producto + "-" + new Date(System.currentTimeMillis()) + "-"
                + file.getOriginalFilename();
    }

}
