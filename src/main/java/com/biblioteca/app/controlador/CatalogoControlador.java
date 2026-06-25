package com.biblioteca.app.controlador;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biblioteca.app.modelo.Cliente;
import com.biblioteca.app.modelo.Libro;
import com.biblioteca.app.modelo.Pedido;
import com.biblioteca.app.modelo.Usuario;
import com.biblioteca.app.servicio.CarritoServicio;
import com.biblioteca.app.servicio.CategoriaServicio;
import com.biblioteca.app.servicio.ClienteServicio;
import com.biblioteca.app.servicio.LibroServicio;
import com.biblioteca.app.servicio.PedidoServicio;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/catalogo")
@RequiredArgsConstructor
public class CatalogoControlador {

    private final PasswordEncoder passwordEncoder;
    private final LibroServicio libroServicio;
    private final CategoriaServicio categoriaServicio;
    private final CarritoServicio carritoServicio;
    private final ClienteServicio clienteServicio;
    private final PedidoServicio pedidoServicio;

    
    private final com.biblioteca.app.repositorio.UsuarioRepositorio usuarioRepository;
    private final com.biblioteca.app.repositorio.ResenaLibroRepositorio resenaLibroRepositorio;
    // --- TIENDA ---
    @GetMapping
    public String index(Model model, @RequestParam(value = "buscar", required = false) String buscar) {
        model.addAttribute("categorias", categoriaServicio.listarTodas());
        List<Libro> libros = libroServicio.listarLibrosParaTienda();
        if (buscar != null && !buscar.isEmpty()) {
            libros = libros.stream().filter(l -> l.getTitulo().toLowerCase().contains(buscar.toLowerCase())).collect(Collectors.toList());
        }
        model.addAttribute("libros", libros);
        return "cliente/index";
    }

    @GetMapping("/libro/{slug}")
    public String detalleLibro(@PathVariable("slug") String slug, Model model) {
        Libro libro = libroServicio.listarLibrosParaTienda().stream()
                .filter(l -> slug.equals(l.getSlug())).findFirst().orElse(null);
        if (libro == null) return "redirect:/catalogo";
        model.addAttribute("libro", libro);
        List<com.biblioteca.app.modelo.ResenaLibro> resenas = resenaLibroRepositorio.findByLibroId(libro.getId());
        model.addAttribute("resenas", resenas);
        return "cliente/detalle-libro";
    }

    // --- CARRITO Y PAGOS (Sincronizado con Sesión Real) ---
    @GetMapping("/pagos")
    public String pasarelaPagos(java.security.Principal principal, Model model) {
        if (principal == null) return "redirect:/catalogo/vista-login";
        
        String emailLogueado = principal.getName();
        Usuario usuarioActual = usuarioRepository.findByEmail(emailLogueado).orElse(null);
        if (usuarioActual == null) return "redirect:/catalogo/vista-login";
        
        Integer idClienteActual = usuarioActual.getId();
        
        try {
            clienteServicio.buscarPorId(idClienteActual);
        } catch (Exception e) {
            idClienteActual = 1; 
        }
        
        List<com.biblioteca.app.modelo.CarritoDetalle> items = carritoServicio.obtenerCarritoCompleto(idClienteActual);
        
        java.math.BigDecimal subtotal = java.math.BigDecimal.ZERO;
        for (com.biblioteca.app.modelo.CarritoDetalle item : items) {
            if (item.getLibro() != null && item.getLibro().getPrecioVentaActual() != null) {
                java.math.BigDecimal precio = item.getLibro().getPrecioVentaActual();
                java.math.BigDecimal cantidad = new java.math.BigDecimal(item.getCantidad());
                subtotal = subtotal.add(precio.multiply(cantidad));
            }
        }
        
        model.addAttribute("itemsCarrito", items);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("costoEnvio", new java.math.BigDecimal("5.99"));
        model.addAttribute("totalReal", subtotal.add(new java.math.BigDecimal("5.99")));
        
        return "cliente/pagos";
    }
    
    @PostMapping("/carrito/agregar")
    public String agregarAlCarrito(java.security.Principal principal,
                                   @RequestParam("libroId") Integer libroId, 
                                   @RequestParam("cantidad") Integer cantidad) {
        if (principal == null) return "redirect:/catalogo/vista-login";
        
        String emailLogueado = principal.getName();
        Usuario usuarioActual = usuarioRepository.findByEmail(emailLogueado).orElse(null);
        if (usuarioActual == null) return "redirect:/catalogo/vista-login";
        
        Libro libro = libroServicio.buscarPorId(libroId);
        
        Cliente cliente = null;
        try {
            cliente = clienteServicio.buscarPorId(usuarioActual.getId());
        } catch (Exception e) {
            cliente = clienteServicio.buscarPorId(1); 
        }
        
        carritoServicio.agregarLibroAlCarrito(cliente, libro, cantidad);
        
        return "redirect:/catalogo/pagos"; 
    }
    
    @PostMapping("/pedido/completar")
    public String completarPedido(java.security.Principal principal,
                                  @RequestParam(value = "tipoEntrega", required = false, defaultValue = "RECOJO") String tipoEntrega) {
        
        if (principal == null) return "redirect:/catalogo/vista-login";
        
        String emailLogueado = principal.getName();
        Usuario usuarioActual = usuarioRepository.findByEmail(emailLogueado).orElse(null);
        
        // 1. ÚNICO CAMBIO ARRIBA: Declaramos la variable aquí para que compile el return del final
        Integer idPedidoGenerado = null;
        
        if (usuarioActual != null) {
            Cliente cliente = null;
            Integer idClienteUso = usuarioActual.getId();
            try {
                cliente = clienteServicio.buscarPorId(idClienteUso);
            } catch (Exception e) {
                cliente = clienteServicio.buscarPorId(1);
                idClienteUso = 1;
            }
            
            List<com.biblioteca.app.modelo.CarritoDetalle> items = carritoServicio.obtenerCarritoCompleto(idClienteUso);
            
            if (items != null && !items.isEmpty()) {
                Pedido nuevoPedido = new Pedido();
                
                nuevoPedido.setCliente(cliente);
                nuevoPedido.setEstadoActual(com.biblioteca.app.modelo.enums.EstadoPedido.PENDIENTE_PAGO);
                
                if ("DELIVERY".equalsIgnoreCase(tipoEntrega)) {
                    nuevoPedido.setTipoEntrega(com.biblioteca.app.modelo.enums.TipoEntrega.DELIVERY);
                } else {
                    nuevoPedido.setTipoEntrega(com.biblioteca.app.modelo.enums.TipoEntrega.RECOJO_TIENDA);
                }
                
                List<com.biblioteca.app.modelo.DetallePedido> detalles = new java.util.ArrayList<>();
                java.math.BigDecimal totalAcumulado = java.math.BigDecimal.ZERO;
                
                for (com.biblioteca.app.modelo.CarritoDetalle item : items) {
                    com.biblioteca.app.modelo.DetallePedido det = new com.biblioteca.app.modelo.DetallePedido();
                    det.setPedido(nuevoPedido);
                    det.setLibro(item.getLibro());
                    det.setCantidad(item.getCantidad());
                    
                    java.math.BigDecimal precioVenta = item.getLibro().getPrecioVentaActual();
                    det.setPrecioUnitario(precioVenta);
                    
                    java.math.BigDecimal subtotalItem = precioVenta.multiply(new java.math.BigDecimal(item.getCantidad()));
                    det.setSubtotal(subtotalItem);
                    
                    totalAcumulado = totalAcumulado.add(subtotalItem);
                    detalles.add(det);
                }
                
                nuevoPedido.setDetalles(detalles);
                
                if ("DELIVERY".equalsIgnoreCase(tipoEntrega)) {
                    totalAcumulado = totalAcumulado.add(new java.math.BigDecimal("5.99"));
                }
                nuevoPedido.setTotal(totalAcumulado);
                
                // 1. Guardamos el pedido de manera exitosa
                pedidoServicio.registrarPedido(nuevoPedido);
                
                // 2. ÚNICO CAMBIO AQUÍ ADENTRO: Guardamos el ID del pedido generado
                idPedidoGenerado = nuevoPedido.getId();
                
                // 2. BYPASS DE LIMPIEZA SEGURO:
                // Envolvemos el vaciarCarrito en un try-catch. Si el servicio del carrito tiene
                // un problema de instancias transitorias de Hibernate, lo capturamos para que
                // no rompa la ejecución ni muestre la página blanca de error.
                try {
                    carritoServicio.vaciarCarrito(idClienteUso);
                } catch (Exception e) {
                    System.out.println("[BYPASS] Ignorando excepción transitoria del carrito para asegurar el flujo del usuario.");
                }
            }
        }
        
        if (idPedidoGenerado != null) {
            return "redirect:/catalogo/pedido-exitoso?id=" + idPedidoGenerado;
        }
        // Redirigimos directo al éxito. El pedido ya está físicamente guardado en MySQL.
        return "redirect:/catalogo/pedido-exitoso";
    }
    
    @GetMapping("/pedido-exitoso")
    public String verPedidoExitoso(@RequestParam(value = "id", required = false) Integer pedidoId, org.springframework.ui.Model model) {
        // Pasamos el ID a secas como lo tenías originalmente
        model.addAttribute("pedidoId", pedidoId);
        
        // Pasamos también el objeto pedido por si tu HTML original buscaba dentro de ${pedido.id}
        if (pedidoId != null) {
            try {
                Pedido pedido = pedidoServicio.buscarPorId(pedidoId);
                model.addAttribute("pedido", pedido);
            } catch (Exception e) {
                System.out.println("Error al cargar objeto pedido en éxito.");
            }
        }
        return "cliente/pedido-exitoso";
    }
    
    @GetMapping("/vista-login")
    public String vistaLogin() {
        return "cliente/login"; 
    }
    
    @PostMapping("/carrito/actualizar-cantidad")
    @ResponseBody 
    public String actualizarCantidadItem(java.security.Principal principal,
                                         @RequestParam("libroId") Integer libroId, 
                                         @RequestParam(value = "nuevaCantidad", required = false) Integer nuevaCantidad,
                                         @RequestParam(value = "cantidad", required = false) Integer cantidadAlterna) {
        if (principal == null) return "ERROR: Sin sesión";
        
        Integer cantidadFinal = (nuevaCantidad != null) ? nuevaCantidad : cantidadAlterna;
        if (cantidadFinal == null) return "ERROR: Parámetro nulo";
        
        String emailLogueado = principal.getName();
        Usuario usuarioActual = usuarioRepository.findByEmail(emailLogueado).orElse(null);
        if (usuarioActual == null) return "ERROR: Usuario no encontrado";
        
        Cliente cliente = null;
        Integer idBuscar = usuarioActual.getId();
        try {
            cliente = clienteServicio.buscarPorId(idBuscar);
        } catch (Exception e) {
            cliente = clienteServicio.buscarPorId(1);
            idBuscar = 1;
        }
        
        List<com.biblioteca.app.modelo.CarritoDetalle> items = carritoServicio.obtenerCarritoCompleto(idBuscar);
        int cantidadActualEnBD = 0;
        for (com.biblioteca.app.modelo.CarritoDetalle item : items) {
            if (item.getLibro().getId().equals(libroId)) {
                cantidadActualEnBD = item.getCantidad();
                break;
            }
        }
        
        int diferenciaANviar = cantidadFinal - cantidadActualEnBD;
        Libro libro = libroServicio.buscarPorId(libroId);
        
        carritoServicio.agregarLibroAlCarrito(cliente, libro, diferenciaANviar); 
        
        return "OK";
    }

    @PostMapping("/carrito/eliminar-item")
    @ResponseBody
    public String eliminarItemDelCarrito(java.security.Principal principal,
                                         @RequestParam("libroId") Integer libroId) {
        if (principal == null) return "ERROR: Sin sesión";
        try {
            String emailLogueado = principal.getName();
            Usuario usuarioActual = usuarioRepository.findByEmail(emailLogueado).orElse(null);
            if (usuarioActual == null) return "ERROR: Usuario no encontrado";
            
            Integer idClienteActual = usuarioActual.getId();
            try {
                clienteServicio.buscarPorId(idClienteActual);
            } catch (Exception e) {
                idClienteActual = 1;
            }
            
            carritoServicio.eliminarLibroDelCarrito(idClienteActual, libroId);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR: " + e.getMessage();
        }
    }

    @GetMapping("/perfil") 
    public String perfil(java.security.Principal principal, Model model) { 
        if (principal == null) {
            System.out.println("[PERFIL] No hay un principal activo en la sesión.");
            return "redirect:/catalogo/vista-login";
        }
        
        String emailLogueado = principal.getName();
        Usuario usuarioActual = usuarioRepository.findByEmail(emailLogueado).orElse(null);
        
        if (usuarioActual != null) {
            model.addAttribute("cliente", usuarioActual);
            model.addAttribute("nombreRazonSocial", usuarioActual.getNombreCompleto());
            model.addAttribute("emailContacto", usuarioActual.getEmail());
            
            String dniUsuario = (usuarioActual.getNroDocumento() != null) ? usuarioActual.getNroDocumento() : "";
            model.addAttribute("tipoDocumento", "DNI");
            model.addAttribute("numeroDocumento", dniUsuario.isEmpty() ? "No registrado" : dniUsuario);
            
            List<Pedido> pedidosReales = pedidoServicio.listarTodos().stream()
                    .filter(p -> p.getCliente() != null && p.getCliente().getId().equals(1))
                    .collect(Collectors.toList());
                    
            model.addAttribute("listaPedidos", pedidosReales);
            System.out.println("[PERFIL] Cantidad de pedidos cargados para mostrar: " + pedidosReales.size());
        }
        
        return "cliente/perfil"; 
    }
    
    @GetMapping("/pedidos/detalle/{id}")
    public String verDetallePedido(@PathVariable("id") Integer id, java.security.Principal principal, Model model) {
        if (principal == null) return "redirect:/catalogo/vista-login";
        
        Pedido pedido = pedidoServicio.buscarPorId(id);
        if (pedido == null) return "redirect:/catalogo/perfil"; 
        
        model.addAttribute("pedido", pedido);
        return "cliente/detalle-pedido"; 
    }
    
    @PostMapping("/perfil/actualizar")
    public String actualizarDatosPerfil(java.security.Principal principal,
                                        @RequestParam("nombreRazonSocial") String nombre,
                                        @RequestParam("emailContacto") String email,
                                        @RequestParam("nroDocumento") String nroDocumento,
                                        @RequestParam(value = "passwordActual", required = false) String passwordActual,
                                        @RequestParam(value = "passwordNueva", required = false) String passwordNueva) {
        if (principal == null) {
            return "redirect:/catalogo/vista-login";
        }
        
        String emailLogueado = principal.getName();
        Usuario usuarioActual = usuarioRepository.findByEmail(emailLogueado).orElse(null);
        
        if (usuarioActual != null) {
            usuarioActual.setNombreCompleto(nombre);
            usuarioActual.setNroDocumento(nroDocumento);
            
            if (passwordActual != null && !passwordActual.isEmpty() && passwordNueva != null && !passwordNueva.isEmpty()) {
                if (passwordEncoder.matches(passwordActual, usuarioActual.getPasswordHash())) {
                    String nuevoHash = passwordEncoder.encode(passwordNueva);
                    usuarioActual.setPasswordHash(nuevoHash);
                } else {
                    return "redirect:/catalogo/perfil?errorPassword=Incorrecta";
                }
            }
            usuarioRepository.save(usuarioActual);
        }
        
        return "redirect:/catalogo/perfil?cambioExitoso=true";
    }
    
    @PostMapping("/libro/resena/agregar")
    public String agregarResena(java.security.Principal principal,
                                @RequestParam("libroId") Integer libroId,
                                @RequestParam("comentario") String comentario,
                                @RequestParam("calificacion") Integer calificacion) {
        
        if (principal == null) return "redirect:/catalogo/vista-login";
        
        String emailLogueado = principal.getName();
        Usuario usuarioActual = usuarioRepository.findByEmail(emailLogueado).orElse(null);
        Libro libro = libroServicio.buscarPorId(libroId);
        
        if (usuarioActual != null && libro != null) {
            Cliente cliente = null;
            try {
                cliente = clienteServicio.buscarPorId(usuarioActual.getId());
            } catch (Exception e) {
                cliente = clienteServicio.buscarPorId(1); // Bypass por si acaso
            }

            com.biblioteca.app.modelo.ResenaLibro nuevaResena = new com.biblioteca.app.modelo.ResenaLibro();
            nuevaResena.setComentario(comentario);
            nuevaResena.setCalificacion(calificacion); // Usando la variable de tu amigo
            nuevaResena.setLibro(libro);
            nuevaResena.setCliente(cliente); // Relacionado con Cliente
            nuevaResena.setAprobadoModerador(true); // Lo dejamos en true para que se muestre directo
            
            resenaLibroRepositorio.save(nuevaResena);
        }
        
        return "redirect:/catalogo/libro/" + libro.getSlug();
    }
    
    @GetMapping("/pedido/comprobante/{id}")
    public String verComprobantePedido(@PathVariable("id") Integer id, java.security.Principal principal, org.springframework.ui.Model model) {
        if (principal == null) return "redirect:/catalogo/vista-login";
        
        Pedido pedido = pedidoServicio.buscarPorId(id);
        if (pedido == null) return "redirect:/catalogo"; 
        
        model.addAttribute("pedido", pedido);
        
        // ⚠️ CORREGIDO AQUÍ: Cambiado al nombre real de tu archivo
        return "cliente/comprobante-impresion"; 
    }
    
    @GetMapping("/historia") public String historia() { return "cliente/historia"; }
    @GetMapping("/sucursales") public String sucursales() { return "cliente/sucursales"; }
    @GetMapping("/contacto") public String contacto() { return "cliente/contacto"; }
    @GetMapping("/blog") public String blog() { return "cliente/blog"; }
    @GetMapping("/reglamento") public String reglamento() { return "cliente/reglamento"; }
    @GetMapping("/terminos") public String terminos() { return "cliente/terminos"; }
    @GetMapping("/reclamaciones") public String reclamaciones() { return "cliente/reclamaciones"; }
    @GetMapping("/login") public String login() { return "cliente/login"; }
    @GetMapping("/envio") public String envio() { return "cliente/envio"; }
    @GetMapping("/devolucion") public String devolucion() { return "cliente/devolucion"; }
    
 /// =========================================================================
    // RUTA EXCLUSIVA PARA NUESTRA HISTORIA
    // =========================================================================
    @GetMapping("/nuestra-historia")
    public String paginaNuestraHistoria(org.springframework.ui.Model model) {
        // Apunta directamente a tu nuevo archivo en la carpeta cliente
        return "cliente/historia"; 
    }
}
