package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_factura")
public class DetalleFacturaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_detalle_factura;
    @ManyToOne
    @JoinColumn(name = "id_factura_per", referencedColumnName = "idFactura")
    private FacturaModel factura;
    @ManyToOne
    @JoinColumn(name = "id_producto_per", referencedColumnName = "id_producto")
    private ProductoModel producto;
    @Column(nullable = false)
    private Integer cantidad;
    @Column(nullable = false)
    private Float precio_unitario;
    @Column(nullable = false)
    private Float subtotal_producto;
    @Column(nullable = false)
    private Float total_iva;

    public Long getId_detalle_factura() {
        return id_detalle_factura;
    }

    public void setId_detalle_factura(Long id_detalle_factura) {
        this.id_detalle_factura = id_detalle_factura;
    }

    public FacturaModel getFactura() {
        return factura;
    }

    public void setFactura(FacturaModel factura) {
        this.factura = factura;
    }

    public ProductoModel getProducto() {
        return producto;
    }

    public void setProducto(ProductoModel producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Float getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(Float precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public Float getSubtotal_producto() {
        return subtotal_producto;
    }

    public void setSubtotal_producto(Float subtotal_producto) {
        this.subtotal_producto = subtotal_producto;
    }

    public Float getTotal_iva() {
        return total_iva;
    }

    public void setTotal_iva(Float total_iva) {
        this.total_iva = total_iva;
    }
}
