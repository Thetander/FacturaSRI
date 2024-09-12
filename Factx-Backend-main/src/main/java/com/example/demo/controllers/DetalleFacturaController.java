package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.CategoriaModel;
import com.example.demo.models.DetalleFacturaModel;
import com.example.demo.models.FacturaModel;
import com.example.demo.models.IvaModel;
import com.example.demo.models.ProductoModel;
import com.example.demo.services.CategoriaService;
import com.example.demo.services.DetalleFacturaService;
import com.example.demo.services.FacturaService;
import com.example.demo.services.IvaService;
import com.example.demo.services.ProductoService;

@RestController
@RequestMapping("/detalleFactura")
public class DetalleFacturaController {
    @Autowired
    DetalleFacturaService detalleFacturaService;
    @Autowired
    FacturaService facturaService;
    @Autowired
    ProductoService productoService;
    @Autowired
    CategoriaService categoriaService;
    @Autowired
    IvaService ivaService;

    @GetMapping()
    public ArrayList<DetalleFacturaModel> obtenerDetalleFacturas() {
        return detalleFacturaService.obteberDetalleFacturas();
    }

    @PostMapping()
    public DetalleFacturaModel guardarDetalleFactura(@RequestParam("factura") String id_factura_per,
            @RequestParam("producto") String id_producto_per,
            @RequestParam("cantidad") String cantidad) {
        DetalleFacturaModel detalleFactura = new DetalleFacturaModel();

        // Obtener el producto por su id
        ProductoModel producto = productoService.obtenerPorId(Long.parseLong(id_producto_per)).get();

        // Obtenemos la factura por su id
        FacturaModel factura = facturaService.obtenerPorId(Long.parseLong(id_factura_per)).get();

        // Verificar si el producto tiene stock
        if (producto.getCantidad() < Integer.parseInt(cantidad) || factura.getEstado() == "Abierta") {
            return null;
        }

        // Obtenemos la categoria del producto
        CategoriaModel categoria = categoriaService.obtenerPorId(producto.getCategoria().getId_categoria()).get();

        // Obtenemos el iva de la categoria
        IvaModel iva = ivaService.obtenerPorId(categoria.getIva().getId_iva()).get();

        detalleFactura.setFactura(factura);
        detalleFactura.setProducto(producto);
        detalleFactura.setCantidad(Integer.parseInt(cantidad));
        detalleFactura.setPrecio_unitario(producto.getPrecio());
        detalleFactura.setSubtotal_producto(producto.getPrecio() * Integer.parseInt(cantidad));
        detalleFactura.setTotal_iva((producto.getPrecio() * Integer.parseInt(cantidad)) * iva.getIva());

        // Actualizamos el stock del producto
        producto.setCantidad(producto.getCantidad() - Integer.parseInt(cantidad));

        return this.detalleFacturaService.guardarDetalleFactura(detalleFactura);
    }

    @GetMapping(path = "/{id}")
    public Optional<DetalleFacturaModel> obtenerDetalleFacturaPorId(@PathVariable("id") Long id) {
        return this.detalleFacturaService.obtenerPorId(id);
    }

    @GetMapping("/query")
    public ArrayList<DetalleFacturaModel> obtenerDetalleFacturaPorFacturaId(@RequestParam("id_factura_per") Long id) {
        return this.detalleFacturaService.obtenerPorFacturaId(id);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminarDetalleFacturaPorId(@PathVariable("id") Long id) {
        boolean ok = this.detalleFacturaService.eliminarDetalleFactura(id);
        if (ok) {
            return "Se eliminó el detalleFactura con id " + id;
        } else {
            return "No se pudo eliminar el detalleFactura con id " + id;
        }
    }

}
