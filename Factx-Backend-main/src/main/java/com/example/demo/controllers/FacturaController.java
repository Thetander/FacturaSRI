package com.example.demo.controllers;

import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXParameters;
import java.security.cert.PKIXRevocationChecker;
import java.security.cert.X509Certificate;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.net.ssl.TrustManagerFactory;
import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.tomcat.util.http.MimeHeaders;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.xml.security.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import com.example.demo.models.ClienteModel;
import com.example.demo.models.DetalleFacturaModel;
import com.example.demo.models.DocumentoModel;
import com.example.demo.models.EmpresaModel;
import com.example.demo.models.FacturaModel;
import com.example.demo.models.FormaPagoModel;
import com.example.demo.models.UsuarioModel;
import com.example.demo.services.ClienteService;
import com.example.demo.services.DetalleFacturaService;
import com.example.demo.services.DocumentoService;
import com.example.demo.services.EmailService;
import com.example.demo.services.EmpresaService;
import com.example.demo.services.FacturaService;
import com.example.demo.services.FormaPagoService;
import com.example.demo.services.UsuarioService;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

import jakarta.xml.soap.*;
import jakarta.xml.ws.BindingProvider;

import org.apache.http.impl.client.CloseableHttpClient;

import java.io.*;
import java.net.URL;

import javax.xml.transform.TransformerFactory;
import org.apache.xml.security.utils.Constants;
import org.w3c.dom.Node;
import javax.xml.crypto.dsig.keyinfo.*;

//

//

@RestController
@RequestMapping("/factura")
public class FacturaController {
        @Autowired
        FacturaService facturaService;
        @Autowired
        ClienteService clienteService;
        @Autowired
        UsuarioService usuarioService;
        @Autowired
        DetalleFacturaService detalleFacturaService;
        @Autowired
        FormaPagoService formaPagoService;
        @Autowired
        EmpresaService empresaService;
        @Autowired
        DocumentoService documentoService;
        @Autowired
        private EmailService emailService;

        @GetMapping()
        public ArrayList<FacturaModel> obtenerFacturas() {
                return facturaService.obteberFacturas();
        }

        @PostMapping("/abrir")
        public FacturaModel guardarFactura(@RequestParam("cliente") String id_cliente_per,
                        @RequestParam("usuario") String id_usuario_per) {

                // Se obtiene el cliente por su id
                ClienteModel cliente = clienteService.obtenerPorId(Long.parseLong(id_cliente_per)).get();

                // Se obtiene el usuario por su id
                UsuarioModel usuario = usuarioService.obtenerPorId(Long.parseLong(id_usuario_per)).get();

                // Obtener la fecha actual de Ecuador en formato dd/MM/yyyy
                String zonaHorariaEcuador = "America/Guayaquil";
                ZonedDateTime fechaEcuador = ZonedDateTime.now(ZoneId.of(zonaHorariaEcuador));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String fecha = fechaEcuador.format(formatter);

                // Generar el número de factura
                FacturaModel factura = new FacturaModel();
                ArrayList<FacturaModel> facturas = facturaService.obteberFacturas();

                if (facturas.isEmpty()) {
                        factura.setNumero_factura(400);
                } else {
                        factura.setNumero_factura(facturas.size() + 401);
                }

                factura.setCliente(cliente);
                factura.setUsuario(usuario);
                factura.setFecha(fecha);
                factura.setEstado("Abierta");

                return facturaService.guardarFactura(factura);
        }

        @PostMapping("/cerrar")
        public FacturaModel cerrarFactura(@RequestParam("factura") String id_factura_per,
                        @RequestParam("pago") String id_pago_per) throws Exception {
                FacturaModel factura = facturaService.obtenerPorId(Long.parseLong(id_factura_per)).get();
                String claveAcceso = generarClaveAcceso(factura.getFecha().replace("/", ""), "01",
                                factura.getUsuario().getEmpresa().getRuc(),
                                "1", "001001", String.format("%09d", factura.getNumero_factura()), "71011173", "1");
                factura.setClave_acceso(claveAcceso);

                // obtener los detalles de una factura
                ArrayList<DetalleFacturaModel> detalles = detalleFacturaService
                                .obtenerPorFacturaId(factura.getIdFactura());

                // Calcular el total de la factura
                float subtotal = 0;
                float totalIva = 0;
                float total = 0;
                for (DetalleFacturaModel detalle : detalles) {
                        subtotal += detalle.getSubtotal_producto();
                        totalIva += detalle.getTotal_iva();
                        total += detalle.getSubtotal_producto() + detalle.getTotal_iva();
                }

                factura.setSubtotal(subtotal);
                factura.setTotal_iva(totalIva);
                factura.setTotal(total);

                // Se obtiene la forma de pago por su id
                FormaPagoModel formaPago = formaPagoService.obtenerPorId(Long.parseLong(id_pago_per)).get();

                factura.setFormaPago(formaPago);

                factura.setEstado("Cerrada");

                // obtener la empresa
                EmpresaModel empresa = empresaService.obtenerPorId(factura.getUsuario().getEmpresa().getId_empresa())
                                .get();

                // obtener el cliente
                ClienteModel cliente = clienteService.obtenerPorId(factura.getCliente().getId_cliente()).get();

                // generar xml
                String xmlFactura = generarXML(factura, detalles, empresa, cliente);
                System.out.println("XML Generado");
                System.out.println(xmlFactura);

                // Firmar el xml
                String xmlFirmado = firmarXML(xmlFactura, factura);
                System.out.println("XML Firmado");
                System.out.println(xmlFirmado);

                // Enviar el xml al SRI
                String respuesta = validarXml(xmlFirmado);
                System.out.println("Respuesta del SRI");
                System.out.println(respuesta);

                // Generar el PDF
                byte[] pdf = generateInvoicePdf(factura, empresa, cliente, detalles);

                // Guardar el XML y el PDF
                ArrayList<String> paths = guardarComprobante(xmlFirmado, claveAcceso, pdf);
                DocumentoModel documento = new DocumentoModel();
                documento.setXml(paths.get(0));
                documento.setPdf(paths.get(1));
                documentoService.guardarDocumento(documento);

                factura.setDocumento(documento);

                System.out.println(cliente.getCorreo());

                if (cliente.getCorreo() != null && !cliente.getCorreo().isEmpty()) {
                        System.out.println("Enviando correo");
                        // System.out.println(cliente.getCorreo());
                        emailService.sendEmailWithAttachments(cliente.getCorreo(), pdf, xmlFirmado,
                                        factura.getClave_acceso());
                }

                return this.facturaService.guardarFactura(factura);
        }

        @GetMapping(path = "/{id}")
        public Optional<FacturaModel> obtenerFacturaPorId(@PathVariable("id") Long id) {
                return this.facturaService.obtenerPorId(id);
        }

        @GetMapping(path = "/query")
        public ArrayList<FacturaModel> obtenerFacturaPorCedulaCliente(@RequestParam("cedula") String cedula) {
                return facturaService.obtenerPorCedulaCliente(cedula);
        }

        @DeleteMapping(path = "/{id}")
        public String eliminarFacturaPorId(@PathVariable("id") Long id) {
                boolean ok = this.facturaService.eliminarFactura(id);
                if (ok) {
                        return "Se eliminó la factura con id " + id;
                } else {
                        return "No se pudo eliminar la factura con id " + id;
                }
        }

        private String generarClaveAcceso(String fecha, String tipoComprobante, String ruc, String ambiente,
                        String serie,
                        String numeroFactura, String codigoNumerico, String tipoEmision) {
                String claveAccesoIncompleta = fecha + tipoComprobante + ruc + ambiente + serie + numeroFactura
                                + codigoNumerico
                                + tipoEmision;
                String dv = getMod11Dv(claveAccesoIncompleta);
                if (dv != null) {
                        return claveAccesoIncompleta + dv;
                } else {
                        return null;
                }
        }

        private String getMod11Dv(String claveAccesoIncompleta) {
                // Invertir la cadena y eliminar . y ,
                String digits = new StringBuilder(claveAccesoIncompleta.replace(".", "").replace(",", "")).reverse()
                                .toString();

                // Verificar que todos los caracteres sean dígitos
                if (!digits.matches("\\d+")) {
                        return null;
                }

                // Inicializar variables
                int sum = 0;
                int factor = 2;

                for (int i = 0; i < digits.length(); i++) {
                        sum += Character.getNumericValue(digits.charAt(i)) * factor;
                        if (factor == 7) {
                                factor = 2;
                        } else {
                                factor++;
                        }
                }

                int dv = 11 - sum % 11;

                if (dv == 10) {
                        return "1";
                } else if (dv == 11) {
                        return "0";
                } else {
                        return String.valueOf(dv);
                }
        }

        public String generarXML(FacturaModel factura, ArrayList<DetalleFacturaModel> detalles, EmpresaModel empresa,
                        ClienteModel cliente) throws Exception {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                // Root element
                Document doc = docBuilder.newDocument();
                Element facturaElement = doc.createElement("factura");
                facturaElement.setAttribute("id", "comprobante");
                facturaElement.setAttribute("version", "1.1.0");
                doc.appendChild(facturaElement);

                // infoTributaria
                Element infoTributaria = doc.createElement("infoTributaria");
                facturaElement.appendChild(infoTributaria);

                addElement(doc, infoTributaria, "ambiente", "1");
                addElement(doc, infoTributaria, "tipoEmision", "1");
                addElement(doc, infoTributaria, "razonSocial", empresa.getRazon_social());
                addElement(doc, infoTributaria, "ruc", empresa.getRuc());
                addElement(doc, infoTributaria, "claveAcceso", factura.getClave_acceso());
                addElement(doc, infoTributaria, "codDoc", "01");
                addElement(doc, infoTributaria, "estab", "001");
                addElement(doc, infoTributaria, "ptoEmi", "100");
                addElement(doc, infoTributaria, "secuencial", String.format("%09d", factura.getNumero_factura()));
                addElement(doc, infoTributaria, "dirMatriz", empresa.getDireccion());

                // infoFactura
                Element infoFactura = doc.createElement("infoFactura");
                facturaElement.appendChild(infoFactura);

                // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                addElement(doc, infoFactura, "fechaEmision", factura.getFecha());
                addElement(doc, infoFactura, "dirEstablecimiento", empresa.getDireccion());
                // addElement(doc, infoFactura, "obligadoContabilidad",
                // empresa.getLleva_contabilidad() ? "SI" : "NO");
                addElement(doc, infoFactura, "obligadoContabilidad", "NO");
                addElement(doc, infoFactura, "tipoIdentificacionComprador", cliente.getCedula().equals("9999999999999")
                                ? "07"
                                : (cliente.getCedula().length() == 13 && cliente.getCedula().endsWith("001") ? "04"
                                                : "05"));
                addElement(doc, infoFactura, "razonSocialComprador", "PRUEBAS SERVICIO DE RENTAS INTERNAS");
                addElement(doc, infoFactura, "identificacionComprador", cliente.getCedula());
                addElement(doc, infoFactura, "totalSinImpuestos",
                                String.format("%.2f", (factura.getSubtotal())).replace(",", "."));
                addElement(doc, infoFactura, "totalDescuento", "0.00");

                // totalConImpuestos
                Element totalConImpuestos = doc.createElement("totalConImpuestos");
                infoFactura.appendChild(totalConImpuestos);

                Element totalImpuesto = doc.createElement("totalImpuesto");
                totalConImpuestos.appendChild(totalImpuesto);

                addElement(doc, totalImpuesto, "codigo", "2");
                // addElement(doc, totalImpuesto, "codigoPorcentaje", factura.getTotal_iva() ==
                // 0.00 ? "0" : "2");
                addElement(doc, totalImpuesto, "codigoPorcentaje", "4");
                addElement(doc, totalImpuesto, "baseImponible",
                                String.format("%.2f", (factura.getSubtotal())).replace(",", "."));
                addElement(doc, totalImpuesto, "valor",
                                String.format("%.2f", factura.getTotal_iva()).replace(",", "."));

                addElement(doc, infoFactura, "propina", "0");
                addElement(doc, infoFactura, "importeTotal",
                                String.format("%.2f", factura.getTotal()).replace(",", "."));
                addElement(doc, infoFactura, "moneda", "DOLAR");

                // pagos
                Element pagos = doc.createElement("pagos");
                infoFactura.appendChild(pagos);

                Element pago = doc.createElement("pago");
                pagos.appendChild(pago);

                addElement(doc, pago, "formaPago", "01");
                addElement(doc, pago, "total", String.format("%.2f", factura.getTotal()).replace(",", "."));

                // detalles
                Element detallesElement = doc.createElement("detalles");
                facturaElement.appendChild(detallesElement);

                for (DetalleFacturaModel detalle : detalles) {
                        Element detalleElement = doc.createElement("detalle");
                        detallesElement.appendChild(detalleElement);

                        addElement(doc, detalleElement, "codigoPrincipal",
                                        String.valueOf(detalle.getProducto().getId_producto()));
                        addElement(doc, detalleElement, "descripcion", detalle.getProducto().getProducto());
                        addElement(doc, detalleElement, "cantidad", String.valueOf(detalle.getCantidad()));
                        addElement(doc, detalleElement, "precioUnitario",
                                        String.format("%.2f", detalle.getPrecio_unitario()).replace(",", "."));
                        addElement(doc, detalleElement, "descuento", "0.00");
                        addElement(doc, detalleElement, "precioTotalSinImpuesto",
                                        String.format("%.2f", detalle.getPrecio_unitario()).replace(",", "."));

                        /*
                         * Element detallesAdicionales = doc.createElement("detallesAdicionales");
                         * detalleElement.appendChild(detallesAdicionales);
                         * 
                         * Element detAdicional = doc.createElement("detAdicional");
                         * detAdicional.setAttribute("nombre", "informacionAdicional");
                         * detAdicional.setAttribute("valor", detalle.getProducto().getProducto());
                         * detallesAdicionales.appendChild(detAdicional);
                         */

                        Element impuestos = doc.createElement("impuestos");
                        detalleElement.appendChild(impuestos);

                        Element impuesto = doc.createElement("impuesto");
                        impuestos.appendChild(impuesto);

                        addElement(doc, impuesto, "codigo", "2");
                        addElement(doc, impuesto, "codigoPorcentaje", "4");
                        addElement(doc, impuesto, "tarifa", "15.0");
                        addElement(doc, impuesto, "baseImponible",
                                        String.format("%.2f", detalle.getPrecio_unitario()).replace(",", "."));
                        addElement(doc, impuesto, "valor",
                                        String.format("%.2f", detalle.getTotal_iva()).replace(",", "."));
                }

                // Convert Document to String
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Evitar la indentación

                StringWriter writer = new StringWriter();
                transformer.transform(new DOMSource(doc), new StreamResult(writer));
                String xmlOutput = writer.getBuffer().toString();

                // xmlOutput = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                // xmlOutput.substring(xmlOutput.indexOf("?>") + 2);
                /*
                 * xmlOutput = xmlOutput.
                 * replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>",
                 * "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                 */

                // return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                // writer.getBuffer().toString();
                return xmlOutput;
        }

        private void addElement(Document doc, Element parent, String name, String value) {
                Element element = doc.createElement(name);
                element.appendChild(doc.createTextNode(value));
                parent.appendChild(element);
        }

        public String firmarXML(String xml, FacturaModel factura) throws Exception {
                // Obtener los datos de la empresa
                EmpresaModel empresa = empresaService.obtenerPorId(factura.getUsuario().getEmpresa().getId_empresa())
                                .get();
                // String claveP12Url = "http://localhost:8080" +
                // empresa.getFirma_electronica();
                // String claveP12Url = "http://34.74.207.191:8080/Factx-Backend" +
                // empresa.getFirma_electronica();
                String claveP12Url = "http://34.74.207.191:8081" + empresa.getFirma_electronica();

                String claveP12Password = empresa.getContrasena_firma_electronica();

                // Descargar el archivo .p12 desde la URL
                byte[] p12Data;
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                        HttpURLConnection connection = (HttpURLConnection) new URL(claveP12Url).openConnection();
                        connection.setRequestMethod("GET");
                        try (InputStream is = connection.getInputStream()) {
                                byte[] buffer = new byte[1024];
                                int bytesRead;
                                while ((bytesRead = is.read(buffer)) != -1) {
                                        baos.write(buffer, 0, bytesRead);
                                }
                        }
                        p12Data = baos.toByteArray();
                }

                // Cargar el almacén de claves desde los datos descargados
                KeyStore keyStore = KeyStore.getInstance("PKCS12");
                keyStore.load(new ByteArrayInputStream(p12Data), claveP12Password.toCharArray());
                KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(
                                keyStore.aliases().nextElement(),
                                new KeyStore.PasswordProtection(claveP12Password.toCharArray()));
                PrivateKey privateKey = pkEntry.getPrivateKey();
                X509Certificate cert = (X509Certificate) pkEntry.getCertificate();

                // Convertir la cadena XML en un documento XML
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setNamespaceAware(true);
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(new StringReader(xml)));

                // Crear la instancia de XMLSignatureFactory
                XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

                // Cambiar la referencia para firmar todo el documento
                Reference ref = fac.newReference("", // Cambiado de "#comprobante" a ""
                                fac.newDigestMethod("http://www.w3.org/2001/04/xmlenc#sha256", null),
                                Collections.singletonList(
                                                fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)),
                                null,
                                null);

                // Crear la SignedInfo
                SignedInfo si = fac.newSignedInfo(
                                fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
                                                (C14NMethodParameterSpec) null),
                                fac.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", null),
                                Collections.singletonList(ref));

                // Crear KeyInfo y agregar el certificado
                KeyInfoFactory kif = fac.getKeyInfoFactory();
                X509Data x509Data = kif.newX509Data(Collections.singletonList(cert));
                KeyInfo ki = kif.newKeyInfo(Collections.singletonList(x509Data));

                // Crear el DOMSignContext y especificar el nodo donde insertar la firma
                NodeList nodeList = doc.getElementsByTagName("infoTributaria"); // Cambia esto según donde quieras
                                                                                // insertar la firma
                Node parentNode = nodeList.item(0).getParentNode();
                DOMSignContext dsc = new DOMSignContext(privateKey, parentNode);

                // Crear el nodo Signature y agregarlo al documento
                XMLSignature signature = fac.newXMLSignature(si, ki);
                Node signatureNode = dsc.getParent();
                signature.sign(dsc);

                // Firmar el documento
                // signature.getXmlSignature().sign(ds);

                // Convertir el documento firmado a cadena
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer trans = tf.newTransformer();
                StringWriter sw = new StringWriter();
                trans.transform(new DOMSource(doc), new StreamResult(sw));

                return sw.toString().replace("URI=\"\"", "URI=\"#comprobante\"");
        }

        public String validarXml(String signedXml) {
                try {
                        // URL del servicio web
                        String url = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline";

                        String xmlBase64 = Base64.getEncoder()
                                        .encodeToString((signedXml.replace("standalone=\"no\"", "")).getBytes("UTF-8"));

                        // Contenido del mensaje SOAP
                        String soapMessage = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ec=\"http://ec.gob.sri.ws.recepcion\">\n"
                                        +
                                        "   <soapenv:Header/>\n" +
                                        "   <soapenv:Body>\n" +
                                        "      <ec:validarComprobante>\n" +
                                        "         <xml>" + xmlBase64 + "</xml>\n" +
                                        "      </ec:validarComprobante>\n" +
                                        "   </soapenv:Body>\n" +
                                        "</soapenv:Envelope>";

                        // Crear cliente HTTP
                        CloseableHttpClient httpClient = HttpClients.createDefault();
                        HttpPost httpPost = new HttpPost(url);

                        // Configurar encabezados
                        httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");
                        httpPost.setHeader("SOAPAction", "");

                        // Establecer cuerpo del mensaje SOAP
                        StringEntity entity = new StringEntity(soapMessage);
                        httpPost.setEntity(entity);

                        // Enviar solicitud HTTP y obtener respuesta
                        CloseableHttpResponse response = httpClient.execute(httpPost);
                        org.apache.http.HttpEntity responseEntity = response.getEntity();

                        // Leer y procesar la respuesta
                        if (responseEntity != null) {
                                // Leer el contenido del flujo de entrada y convertirlo a una cadena de texto
                                InputStream inputStream = responseEntity.getContent();
                                String responseString = convertInputStreamToString(inputStream);
                                System.out.println("Respuesta del servicio:");
                                return responseString;
                        }
                        // Cerrar el cliente HTTP
                        httpClient.close();
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }

        // Método para convertir un InputStream a una cadena de texto
        private String convertInputStreamToString(InputStream inputStream) throws IOException {
                StringBuilder stringBuilder = new StringBuilder();
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                                stringBuilder.append(line);
                        }
                }
                return stringBuilder.toString();
        }

        public byte[] generateInvoicePdf(FacturaModel factura, EmpresaModel empresa, ClienteModel cliente,
                        List<DetalleFacturaModel> detallesFactura) throws IOException {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                PdfWriter writer = new PdfWriter(baos);
                PdfDocument pdfDoc = new PdfDocument(writer);
                com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDoc);

                PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

                // Title
                // document.add(new Paragraph("Factura
                // Electrónica").setFont(font).setFontSize(18));

                // Company and Invoice Info
                /*
                 * try {
                 * ImageData imageData = ImageDataFactory.create("http://localhost:8080" +
                 * empresa.getLogo());
                 * Image logo = new Image(imageData).scaleToFit(100, 50);
                 * document.add(logo);
                 * } catch (IOException e) {
                 * // Handle the exception if logo loading fails
                 * System.err.println("Failed to load company logo: " + e.getMessage());
                 * // Optionally, you can add a placeholder or handle this differently
                 * }
                 */

                document.add(new Paragraph("\n"));

                Table table = new Table(UnitValue.createPercentArray(new float[] { 25, 75 }))
                                .useAllAvailableWidth();

                table.addCell(new Paragraph("RUC:").setFont(font).setFontSize(10)
                                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                .setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE));
                table.addCell(new Paragraph(empresa.getRuc()).setFont(font).setFontSize(8).setBorder(Border.NO_BORDER)
                                .setVerticalAlignment(VerticalAlignment.MIDDLE));

                table.addCell(
                                new Paragraph("N. Factura:").setFont(font).setFontSize(10)
                                                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                                .setBorder(Border.NO_BORDER)
                                                .setVerticalAlignment(VerticalAlignment.MIDDLE));
                table.addCell(new Paragraph(factura.getNumero_factura().toString()).setFontSize(8).setFont(font)
                                .setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE));

                table.addCell(
                                new Paragraph("Fecha:").setFont(font).setFontSize(10)
                                                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                                .setBorder(Border.NO_BORDER)
                                                .setVerticalAlignment(VerticalAlignment.MIDDLE));
                table.addCell(
                                new Paragraph(factura.getFecha().toString()).setFont(font).setFontSize(8)
                                                .setBorder(Border.NO_BORDER)
                                                .setVerticalAlignment(VerticalAlignment.MIDDLE));

                table.addCell(
                                new Paragraph("Ambiente:").setFont(font).setFontSize(10)
                                                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                                .setBorder(Border.NO_BORDER)
                                                .setVerticalAlignment(VerticalAlignment.MIDDLE));
                table.addCell(new Paragraph(empresa.getDesarrollo() ? "Desarrollo" : "Producción").setFont(font)
                                .setFontSize(8)
                                .setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE));

                table.addCell(
                                new Paragraph("C. Acceso:").setFont(font).setFontSize(10)
                                                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                                .setBorder(Border.NO_BORDER)
                                                .setVerticalAlignment(VerticalAlignment.MIDDLE));
                table.addCell(
                                new Paragraph(factura.getClave_acceso()).setFont(font).setFontSize(7)
                                                .setBorder(Border.NO_BORDER)
                                                .setVerticalAlignment(VerticalAlignment.MIDDLE));

                // document.add(table);
                // Crear una tabla con 2 columnas
                Table content = new Table(UnitValue.createPercentArray(new float[] { 50, 50 })).useAllAvailableWidth();

                // ImageData imageData = ImageDataFactory.create("http://localhost:8080" +
                // empresa.getLogo());
                // ImageData imageData =
                // ImageDataFactory.create("http://34.74.207.191:8080/Factx-Backend" +
                // empresa.getLogo());
                ImageData imageData = ImageDataFactory.create("http://34.74.207.191:8081" + empresa.getLogo());

                Image logo = new Image(imageData).scaleToFit(100, 50);

                Table table2 = new Table(UnitValue.createPercentArray(new float[] { 1 })).useAllAvailableWidth();

                Table table3 = new Table(UnitValue.createPercentArray(new float[] { 40, 60 })).useAllAvailableWidth();
                table3.addCell(new Paragraph("Razón Social:").setFont(font).setFontSize(10)
                                .setBackgroundColor(ColorConstants.LIGHT_GRAY).setBorder(Border.NO_BORDER)
                                .setVerticalAlignment(VerticalAlignment.MIDDLE));
                table3.addCell(
                                new Paragraph(empresa.getRazon_social()).setFont(font).setFontSize(8)
                                                .setBorder(Border.NO_BORDER)
                                                .setVerticalAlignment(VerticalAlignment.MIDDLE));
                table3.addCell(new Paragraph("Dirección:").setFont(font).setFontSize(10)
                                .setBackgroundColor(ColorConstants.LIGHT_GRAY).setBorder(Border.NO_BORDER)
                                .setVerticalAlignment(VerticalAlignment.MIDDLE));
                table3.addCell(new Paragraph(empresa.getDireccion()).setFont(font).setFontSize(8)
                                .setBorder(Border.NO_BORDER)
                                .setVerticalAlignment(VerticalAlignment.MIDDLE));
                table3.addCell(new Paragraph("Lleva Contabilidad:").setFont(font).setFontSize(10)
                                .setBackgroundColor(ColorConstants.LIGHT_GRAY).setBorder(Border.NO_BORDER)
                                .setVerticalAlignment(VerticalAlignment.MIDDLE));
                table3.addCell(new Paragraph(empresa.getLleva_contabilidad() ? "SI" : "NO").setFont(font).setFontSize(8)
                                .setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE));

                table2.addCell(new Paragraph().add(logo).setBorder(Border.NO_BORDER)
                                .setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER));
                table2.addCell(
                                new Cell().add(table3).setBorder(Border.NO_BORDER)
                                                .setVerticalAlignment(VerticalAlignment.MIDDLE));

                content.addCell(
                                new Cell().add(table2).setBorder(Border.NO_BORDER)
                                                .setVerticalAlignment(VerticalAlignment.MIDDLE));
                content.addCell(
                                new Cell().add(table).setBorder(Border.NO_BORDER)
                                                .setVerticalAlignment(VerticalAlignment.MIDDLE));

                document.add(content);

                document.add(new Paragraph("\n"));

                // Client Info
                table = new Table(UnitValue.createPercentArray(new float[] { 2, 5 }))
                                .useAllAvailableWidth();

                table.addCell(new Paragraph("Cliente:").setFont(font).setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                .setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(cliente.getNombre() + " " + cliente.getApellido()).setFont(font)
                                .setBorder(Border.NO_BORDER));

                table.addCell(new Paragraph("RUC:").setFont(font).setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                .setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(cliente.getCedula()).setFont(font).setBorder(Border.NO_BORDER));

                table.addCell(new Paragraph("Dirección:").setFont(font).setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                .setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(cliente.getDireccion()).setFont(font).setBorder(Border.NO_BORDER));

                table.addCell(new Paragraph("Teléfono:").setFont(font).setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                .setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(cliente.getTelefono()).setFont(font).setBorder(Border.NO_BORDER));

                table.addCell(new Paragraph("Correo electrónico:").setFont(font)
                                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                .setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(cliente.getCorreo()).setFont(font).setBorder(Border.NO_BORDER));

                document.add(table);

                document.add(new Paragraph("\n"));

                // Invoice Details
                table = new Table(UnitValue.createPercentArray(new float[] { 1, 3, 2, 2 }))
                                .useAllAvailableWidth();

                table.addHeaderCell(new Paragraph("Cantidad").setFont(font)
                                .setBackgroundColor(ColorConstants.LIGHT_GRAY));
                table.addHeaderCell(new Paragraph("Producto").setFont(font)
                                .setBackgroundColor(ColorConstants.LIGHT_GRAY));
                table.addHeaderCell(new Paragraph("V. Unitario").setFont(font)
                                .setBackgroundColor(ColorConstants.LIGHT_GRAY));
                table.addHeaderCell(new Paragraph("V. Total").setFont(font)
                                .setBackgroundColor(ColorConstants.LIGHT_GRAY));

                for (DetalleFacturaModel detalle : detallesFactura) {
                        table.addCell(new Paragraph(String.valueOf(detalle.getCantidad())).setFont(font));
                        table.addCell(new Paragraph(detalle.getProducto().getProducto()).setFont(font)); // Assuming
                                                                                                         // getProducto()
                                                                                                         // returns the
                                                                                                         // product
                        // table.addCell(new
                        // Paragraph(String.valueOf(detalle.getPrecio_unitario())).setFont(font));

                        String precioUnitarioFormateado = String.format("%.2f", detalle.getPrecio_unitario());
                        table.addCell(new Paragraph("$ " + precioUnitarioFormateado).setFont(font));

                        // table.addCell(new
                        // Paragraph(String.valueOf(detalle.getSubtotal_producto())).setFont(font));
                        String subtotalProductoFormateado = String.format("%.2f", detalle.getSubtotal_producto());
                        table.addCell(new Paragraph("$ " + subtotalProductoFormateado).setFont(font));
                }

                document.add(table);

                // Subtotal, IVA, and Total
                table = new Table(UnitValue.createPercentArray(new float[] { 2, 1 })).useAllAvailableWidth();

                // Valor del subtotal
                table.addCell(new Paragraph("Subtotal:").setFont(font).setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                .setBorder(Border.NO_BORDER));
                String subtotalFormateado = String.format("%.2f", factura.getSubtotal());
                table.addCell(new Paragraph("$ " + subtotalFormateado).setFont(font).setBorder(Border.NO_BORDER));
                /*
                 * table.addCell(new
                 * Paragraph(String.valueOf(factura.getSubtotal())).setFont(font)
                 * .setBorder(Border.NO_BORDER));
                 */

                // Valor del IVA
                table.addCell(new Paragraph("IVA:").setFont(font).setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                .setBorder(Border.NO_BORDER));
                String totalIvaFormateado = String.format("%.2f", factura.getTotal_iva());
                table.addCell(new Paragraph("$ " + totalIvaFormateado).setFont(font).setBorder(Border.NO_BORDER));
                /*
                 * table.addCell(new
                 * Paragraph(String.valueOf(factura.getTotal_iva())).setFont(font)
                 * .setBorder(Border.NO_BORDER));
                 */

                // Valor del total
                table.addCell(new Paragraph("Total:").setFont(font).setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                .setBorder(Border.NO_BORDER));
                String totalFormateado = String.format("%.2f", factura.getTotal());
                table.addCell(new Paragraph("$ " + totalFormateado).setFont(font).setBorder(Border.NO_BORDER));
                /*
                 * table.addCell(new Paragraph(String.valueOf(factura.getTotal())).setFont(font)
                 * .setBorder(Border.NO_BORDER));
                 */

                document.add(table);
                document.close();

                return baos.toByteArray();
        }

        public ArrayList<String> guardarComprobante(String xml, String claveAcceso, byte[] pdf) {
                // Guardar el XML
                String uploadDir = System.getProperty("user.dir") + "/uploads/documents/xml";

                // Crear directorio si no existe
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                        dir.mkdirs();
                }

                // Guardar archivo
                String xmlPath = uploadDir + "/" + claveAcceso + ".xml";
                try {
                        Files.write(Paths.get(xmlPath), xml.getBytes());
                } catch (IOException e) {
                        e.printStackTrace();
                }

                // Guardar el PDF
                uploadDir = System.getProperty("user.dir") + "/uploads/documents/pdf";

                // Crear directorio si no existe
                dir = new File(uploadDir);
                if (!dir.exists()) {
                        dir.mkdirs();
                }

                // Guardar archivo
                String pdfPath = uploadDir + "/" + claveAcceso + ".pdf";
                try {
                        Files.write(Paths.get(pdfPath), pdf);
                } catch (IOException e) {
                        e.printStackTrace();
                }

                return new ArrayList<String>() {
                        {
                                add("/uploads/documents/xml/" + claveAcceso + ".xml");
                                add("/uploads/documents/pdf/" + claveAcceso + ".pdf");
                        }
                };
        }
}