package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "factura")
public class FacturaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long idFactura;
    @ManyToOne
    @JoinColumn(name = "id_cliente_per", referencedColumnName = "id_cliente")
    private ClienteModel cliente;
    @ManyToOne
    @JoinColumn(name = "id_usuario_per", referencedColumnName = "id_usuario")
    private UsuarioModel usuario;
    @ManyToOne
    @JoinColumn(name = "id_forma_pago_per", referencedColumnName = "id_forma_pago")
    private FormaPagoModel formaPago;
    @ManyToOne
    @JoinColumn(name = "id_documento_per", referencedColumnName = "id_documento")
    private DocumentoModel documento;
    @Column(unique = true)
    private Integer numero_factura;
    @Column(unique = true)
    private String clave_acceso;
    @Column()
    private String fecha;
    @Column()
    private Float subtotal;
    @Column()
    private Float total_iva;
    @Column()
    private Float total;
    @Column()
    private String estado;
    public Long getIdFactura() {
        return idFactura;
    }
    public void setIdFactura(Long idFactura) {
        this.idFactura = idFactura;
    }
    public ClienteModel getCliente() {
        return cliente;
    }
    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }
    public UsuarioModel getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }
    public FormaPagoModel getFormaPago() {
        return formaPago;
    }
    public void setFormaPago(FormaPagoModel formaPago) {
        this.formaPago = formaPago;
    }
    public DocumentoModel getDocumento() {
        return documento;
    }
    public void setDocumento(DocumentoModel documento) {
        this.documento = documento;
    }
    public Integer getNumero_factura() {
        return numero_factura;
    }
    public void setNumero_factura(Integer numero_factura) {
        this.numero_factura = numero_factura;
    }
    public String getClave_acceso() {
        return clave_acceso;
    }
    public void setClave_acceso(String clave_acceso) {
        this.clave_acceso = clave_acceso;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public Float getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(Float subtotal) {
        this.subtotal = subtotal;
    }
    public Float getTotal_iva() {
        return total_iva;
    }
    public void setTotal_iva(Float total_iva) {
        this.total_iva = total_iva;
    }
    public Float getTotal() {
        return total;
    }
    public void setTotal(Float total) {
        this.total = total;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    
}
