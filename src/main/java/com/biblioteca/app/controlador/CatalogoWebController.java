package com.biblioteca.app.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biblioteca.app.modelo.Pedido;
import com.biblioteca.app.servicio.CategoriaServicio;
import com.biblioteca.app.servicio.LibroServicio;
import com.biblioteca.app.servicio.PedidoServicio;




import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/catalogo")
@RequiredArgsConstructor 
public class CatalogoWebController {

    private final LibroServicio libroServicio;
    private final CategoriaServicio categoriaServicio;
    private final PedidoServicio pedidoServicio;

    @GetMapping
    public String verCatalogoPublico(Model model) {
        model.addAttribute("categorias", categoriaServicio.listarTodas());
        model.addAttribute("libros", libroServicio.listarLibrosParaTienda());
        
        return "index"; 
    }
    
    @GetMapping("/vista-login")
    public String verLogin() {
        return "login"; 
    }

    @GetMapping("/registro")
    public String verRegistro() {
        return "registro"; 
    }
    
    @GetMapping("/detalle/{id}")
    public String verDetalleLibro(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("libro", libroServicio.buscarPorId(id));
        return "detalle-libro";
    }
    
    @GetMapping("/checkout") 
    public String verFinalizarCompra(Model model) {
        return "pagos"; 
    }
    
    @GetMapping("/perfil")
    public String verPerfilUsuario(Model model) {
        
        
    	// 1. Simulamos la cabecera usando tu modelo REAL de Cliente
        com.biblioteca.app.modelo.Cliente cliente = new com.biblioteca.app.modelo.Cliente();
        cliente.setId(1); // O 1L si tu ID es tipo Long
        cliente.setNombreRazonSocial("André Letona"); 
        //cliente.setTipoDocumento("DNI");
        cliente.setNumeroDocumento("74839201");
        cliente.setEmailContacto("andre@ejemplo.com");
        cliente.setTelefono("+51 987 654 321");
        
        model.addAttribute("cliente", cliente);

        // 2. Simulamos la lista de Pedidos
        java.util.List<java.util.Map<String, Object>> pedidos = new java.util.ArrayList<>();
        java.util.Map<String, Object> p1 = new java.util.HashMap<>();
        p1.put("id", "00123");
        p1.put("fechaPedido", java.time.LocalDateTime.now().minusDays(3));
        p1.put("tipoEntrega", "Delivery");
        p1.put("total", "120.00");
        p1.put("estadoActual", new EnumSimulado("ENTREGADO")); // Mapea el estado en verde
        pedidos.add(p1);
        
        java.util.Map<String, Object> p2 = new java.util.HashMap<>();
        p2.put("id", "00145");
        p2.put("fechaPedido", java.time.LocalDateTime.now().minusDays(1));
        p2.put("tipoEntrega", "Recojo en Tienda");
        p2.put("total", "85.00");
        p2.put("estadoActual", new EnumSimulado("PENDIENTE_PAGO")); // Mapea el estado en amarillo
        pedidos.add(p2);
        
        model.addAttribute("listaPedidos", pedidos);

        // 3. Simulamos la lista de Préstamos
        java.util.List<java.util.Map<String, Object>> prestamos = new java.util.ArrayList<>();
        java.util.Map<String, Object> pr1 = new java.util.HashMap<>();
        pr1.put("id", 1);
        pr1.put("fechaPrestamo", java.time.LocalDateTime.now().minusDays(5));
        pr1.put("fechaDevolucionEsperada", java.time.LocalDateTime.now().plusDays(10));
        pr1.put("costoAlquiler", "8.00");
        pr1.put("estado", new EnumSimulado("EN_CURSO"));
        
        java.util.Map<String, Object> libro = new java.util.HashMap<>();
        libro.put("titulo", "El Arte de la Ficción");
        libro.put("imagenPortada", "https://images.unsplash.com/photo-1544947950-fa07a98d237f?q=80&w=200");
        
        java.util.Map<String, Object> ejemplar = new java.util.HashMap<>();
        ejemplar.put("libro", libro);
        pr1.put("ejemplarBiblioteca", ejemplar);
        
        prestamos.add(pr1);
        model.addAttribute("listaPrestamos", prestamos);

        // 4. Simulamos la lista de Multas
        java.util.List<java.util.Map<String, Object>> multas = new java.util.ArrayList<>();
        java.util.Map<String, Object> m1 = new java.util.HashMap<>();
        m1.put("motivo", "Retraso en devolución (3 días)");
        m1.put("monto", "15.00");
        m1.put("estado", new EnumSimulado("PENDIENTE"));
        
        java.util.Map<String, Object> refPrestamo = new java.util.HashMap<>();
        refPrestamo.put("id", 1);
        m1.put("prestamo", refPrestamo);
        
        multas.add(m1);
        model.addAttribute("listaMultas", multas);

        return "perfil";
        
        
       
    }
    
    public static class EnumSimulado {
        private String valor;
        public EnumSimulado(String valor) { this.valor = valor; }
        public String name() { return this.valor; }
    }
    
    @org.springframework.web.bind.annotation.GetMapping("/pedidos/detalle/{id}")
    public String verDetallePedido(@org.springframework.web.bind.annotation.PathVariable("id") String id, Model model) {
        
        // 1. Objeto principal PEDIDO
        java.util.Map<String, Object> pedido = new java.util.HashMap<>();
        pedido.put("id", id); 
        pedido.put("fechaPedido", java.time.LocalDateTime.now().minusDays(2));
        pedido.put("tipoEntrega", "Delivery");
        pedido.put("total", 300.90);
        pedido.put("estadoActual", new EnumSimulado("ENTREGADO")); // Mapea al color verde

        // 2. CLIENTE
        java.util.Map<String, Object> cliente = new java.util.HashMap<>();
        cliente.put("nombreRazonSocial", "André Letona");
        pedido.put("cliente", cliente);

        // 3. DIRECCIÓN DE ENVÍO
        java.util.Map<String, Object> direccionEnvio = new java.util.HashMap<>();
        direccionEnvio.put("direccionCompleta", "Av. La Playa 456, Mz. B Lote 5");
        direccionEnvio.put("ciudad", "Ventanilla");
        direccionEnvio.put("codigoPostal", "07051");
        pedido.put("direccionEnvio", direccionEnvio);

        // 4. ENVÍO (Transportista)
        java.util.Map<String, Object> envio = new java.util.HashMap<>();
        envio.put("transportista", "Olva Courier");
        envio.put("numeroTracking", "DLV-2026-8843");
        pedido.put("envio", envio);

        // 5. HISTORIAL DE ESTADOS (La línea de tiempo verde)
        java.util.List<java.util.Map<String, Object>> historial = new java.util.ArrayList<>();
        
        java.util.Map<String, Object> h1 = new java.util.HashMap<>();
        h1.put("estado", new EnumSimulado("PENDIENTE_PAGO"));
        h1.put("fechaCambio", java.time.LocalDateTime.now().minusDays(2).minusHours(4));
        historial.add(h1);

        java.util.Map<String, Object> h2 = new java.util.HashMap<>();
        h2.put("estado", new EnumSimulado("PAGADO"));
        h2.put("fechaCambio", java.time.LocalDateTime.now().minusDays(2));
        historial.add(h2);

        java.util.Map<String, Object> h3 = new java.util.HashMap<>();
        h3.put("estado", new EnumSimulado("ENVIADO"));
        h3.put("fechaCambio", java.time.LocalDateTime.now().minusDays(1));
        historial.add(h3);

        java.util.Map<String, Object> h4 = new java.util.HashMap<>();
        h4.put("estado", new EnumSimulado("ENTREGADO"));
        h4.put("fechaCambio", java.time.LocalDateTime.now().minusHours(2));
        historial.add(h4);

        pedido.put("historial", historial);

        // 6. COMPROBANTE FISCAL
        java.util.Map<String, Object> comprobante = new java.util.HashMap<>();
        comprobante.put("tipoComprobante", "Boleta de Venta");
        comprobante.put("serie", "B001");
        comprobante.put("numero", "00012543");
        pedido.put("comprobante", comprobante);

        // 7. DETALLES DEL PEDIDO (Los libros comprados)
        java.util.List<java.util.Map<String, Object>> detalles = new java.util.ArrayList<>();
        
        // Primer libro
        java.util.Map<String, Object> d1 = new java.util.HashMap<>();
        d1.put("cantidad", 1);
        d1.put("precioUnitario", 85.00);
        d1.put("subtotal", 85.00);
        
        java.util.Map<String, Object> l1 = new java.util.HashMap<>();
        l1.put("titulo", "El Arte de la Ficción");
        l1.put("imagenPortada", "https://images.unsplash.com/photo-1544947950-fa07a98d237f?q=80&w=200");
        l1.put("formato", new EnumSimulado("Físico")); // Simula el ENUM de formato
        
        java.util.List<java.util.Map<String, Object>> autores1 = new java.util.ArrayList<>();
        java.util.Map<String, Object> a1 = new java.util.HashMap<>();
        a1.put("nombre", "David Lodge");
        autores1.add(a1);
        l1.put("autores", autores1);
        
        d1.put("libro", l1);
        detalles.add(d1);

        // Segundo libro
        java.util.Map<String, Object> d2 = new java.util.HashMap<>();
        d2.put("cantidad", 1);
        d2.put("precioUnitario", 95.00);
        d2.put("subtotal", 95.00);
        
        java.util.Map<String, Object> l2 = new java.util.HashMap<>();
        l2.put("titulo", "Viaje a las Estrellas");
        l2.put("imagenPortada", "https://images.unsplash.com/photo-1614730321146-b6fa6a46bcb4?q=80&w=200"); // Una portada de ciencia ficción
        l2.put("formato", new EnumSimulado("Físico"));
        
        java.util.List<java.util.Map<String, Object>> autores2 = new java.util.ArrayList<>();
        java.util.Map<String, Object> a2 = new java.util.HashMap<>();
        a2.put("nombre", "Ana Kepler");
        autores2.add(a2);
        l2.put("autores", autores2);
        
        d2.put("libro", l2);
        detalles.add(d2);

        // 8. MÉTODO DE PAGO
        java.util.Map<String, Object> pago = new java.util.HashMap<>();
        pago.put("metodoPago", "Yape");
        pedido.put("pago", pago);

        // Empaquetamos todo en el modelo
        pedido.put("detalles", detalles);
        model.addAttribute("pedido", pedido);

        // Carga la plantilla HTML que acabamos de crear
        return "detalle-pedido"; 
    }
    
    @GetMapping("/pedidos/comprobante/{id}")
    public String descargarComprobante(@PathVariable("id") String id, Model model) {
        
        // 1. Creamos un pedido simulado para que la plantilla no explote
        java.util.Map<String, Object> pedidoSimulado = new java.util.HashMap<>();
        pedidoSimulado.put("id", id);
        pedidoSimulado.put("fechaPedido", java.time.LocalDateTime.now());
        pedidoSimulado.put("total", 300.90);
        
        // 2. Simulamos el cliente dentro del pedido
        java.util.Map<String, Object> cliente = new java.util.HashMap<>();
        cliente.put("nombreRazonSocial", "André Letona");
        cliente.put("numeroDocumento", "74839201");
        pedidoSimulado.put("cliente", cliente);
        
        // 3. Simulamos un libro en la lista de detalles
        java.util.List<java.util.Map<String, Object>> detalles = new java.util.ArrayList<>();
        java.util.Map<String, Object> d1 = new java.util.HashMap<>();
        d1.put("cantidad", 1);
        d1.put("precioUnitario", 85.00);
        d1.put("subtotal", 85.00);
        
        java.util.Map<String, Object> l1 = new java.util.HashMap<>();
        l1.put("titulo", "El Arte de la Ficción");
        d1.put("libro", l1);
        detalles.add(d1);
        
        pedidoSimulado.put("detalles", detalles);
        
        // Enviamos el pedido simulado a la vista
        model.addAttribute("pedido", pedidoSimulado);
        
        return "comprobante-impresion"; 
    }
    
}