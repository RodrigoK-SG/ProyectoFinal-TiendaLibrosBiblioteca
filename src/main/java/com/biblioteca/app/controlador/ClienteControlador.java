package com.biblioteca.app.controlador;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.biblioteca.app.modelo.Cliente;
import com.biblioteca.app.modelo.DireccionEnvio;
import com.biblioteca.app.modelo.Pedido;
import com.biblioteca.app.servicio.CarritoServicio;
import com.biblioteca.app.servicio.ClienteServicio;
import com.biblioteca.app.servicio.DireccionEnvioServicio;
import com.biblioteca.app.servicio.PedidoServicio;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteControlador {

    // Inyectamos los servicios exactos de Rodrigo
    private final ClienteServicio clienteServicio;
    private final PedidoServicio pedidoServicio;
    private final DireccionEnvioServicio direccionServicio;
    private final CarritoServicio carritoServicio;

    // --- LECTURA: Panel de Perfil ---
    @GetMapping("/perfil")
    public String perfil(Model model) {
        // Simulamos al cliente logueado (ID 1) hasta que conecten Spring Security
        Integer idClienteActual = 1;
        Cliente cliente = clienteServicio.buscarPorId(idClienteActual);
        
        // Filtramos manualmente los pedidos de este cliente (Solución temporal)
        List<Pedido> misPedidos = pedidoServicio.listarTodos().stream()
                .filter(p -> p.getCliente() != null && p.getCliente().getId().equals(idClienteActual))
                .collect(Collectors.toList());

        model.addAttribute("cliente", cliente);
        model.addAttribute("misPedidos", misPedidos);
        
        // Las tablas de Préstamos y Reservas irán aquí cuando Rodrigo cree esos servicios
        
        return "cliente/perfil";
    }

    // --- LECTURA: Pasarela de Pagos / Carrito ---
    @GetMapping("/pagos")
    public String pasarelaPagos(Model model) {
        Integer idClienteActual = 1; 
        
        // Ahora coincide exactamente con el retorno del servicio
        List<com.biblioteca.app.modelo.CarritoDetalle> items = carritoServicio.obtenerCarritoCompleto(idClienteActual);
        
        BigDecimal subtotal = BigDecimal.ZERO;
        for (com.biblioteca.app.modelo.CarritoDetalle item : items) {
            // Accedemos a los métodos reales del modelo Libro
            BigDecimal precio = item.getLibro().getPrecioVentaActual();
            BigDecimal cantidad = new BigDecimal(item.getCantidad());
            subtotal = subtotal.add(precio.multiply(cantidad));
        }
        
        model.addAttribute("itemsCarrito", items);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("costoEnvio", new BigDecimal("5.99"));
        model.addAttribute("totalReal", subtotal.add(new BigDecimal("5.99")));
        
        return "cliente/pagos";
    }

    // --- LECTURA Y ESCRITURA: Envíos y Direcciones ---
    @GetMapping("/envio")
    public String informacionEnvio(Model model) {
        Integer idClienteActual = 1; // Cliente simulado
        
        // Usamos el método exacto que Rodrigo creó para traer las direcciones
        List<DireccionEnvio> direcciones = direccionServicio.listarDireccionesPorCliente(idClienteActual);
        model.addAttribute("direcciones", direcciones);
        
        return "cliente/envio";
    }

    @PostMapping("/envio/nueva-direccion")
    public String guardarDireccion(@RequestParam("etiqueta") String etiqueta,
                                   @RequestParam("direccionCompleta") String direccionCompleta,
                                   @RequestParam("ciudad") String ciudad,
                                   @RequestParam("codigoPostal") String codigoPostal) {
        
        // Obtenemos al cliente actual
        Cliente cliente = clienteServicio.buscarPorId(1);
        
        // Armamos el objeto tal como lo espera JPA
        DireccionEnvio nuevaDireccion = new DireccionEnvio();
        nuevaDireccion.setCliente(cliente);
        nuevaDireccion.setEtiqueta(etiqueta);
        nuevaDireccion.setDireccionCompleta(direccionCompleta);
        nuevaDireccion.setCiudad(ciudad);
        nuevaDireccion.setCodigoPostal(codigoPostal);
        
        // Guardamos usando el servicio de Rodrigo
        direccionServicio.guardar(nuevaDireccion);
        
        return "redirect:/cliente/envio";
    }

    // --- ESCRITURA: Finalizar la Compra ---
    @PostMapping("/pedido/finalizar")
    public String registrarPedido(@RequestParam("metodoPago") String metodoPago, 
                                  @RequestParam("direccionId") Integer direccionId) {
        
        Cliente cliente = clienteServicio.buscarPorId(1);
        
        // Armamos la carcasa básica del pedido
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setCliente(cliente);
        
        // Usamos la lógica transaccional de Rodrigo para registrar todo
        Pedido pedidoGuardado = pedidoServicio.registrarPedido(nuevoPedido);
        
        // Vaciamos el carrito tras la compra exitosa
        carritoServicio.vaciarCarrito(cliente.getId());
        
        return "redirect:/cliente/pedido/" + pedidoGuardado.getId();
    }

    // --- LECTURA: Seguimiento y Comprobantes ---
    @GetMapping("/pedido/{id}")
    public String detallePedido(@PathVariable("id") Integer id, Model model) {
        // Traemos el pedido usando el servicio exacto
        Pedido pedido = pedidoServicio.buscarPorId(id);
        model.addAttribute("pedido", pedido);
        
        return "cliente/detalle-pedido";
    }

    @GetMapping("/pedido/{id}/comprobante")
    public String comprobanteImpresion(@PathVariable("id") Integer id, Model model) {
        Pedido pedido = pedidoServicio.buscarPorId(id);
        model.addAttribute("pedido", pedido);
        // Cuando exista ComprobanteServicio, lo inyectaremos aquí
        
        return "cliente/comprobante-impresion";
    }

    // --- Vistas Estáticas ---
    @GetMapping("/devolucion") 
    public String devolucion() { 
        return "cliente/devolucion"; 
    }
}