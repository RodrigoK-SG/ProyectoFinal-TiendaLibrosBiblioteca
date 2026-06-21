document.addEventListener("DOMContentLoaded", function () {
    
    // Intercepción AJAX para los botones de Añadir al Carrito
    const addCartButtons = document.querySelectorAll(".btn-add-cart");
    const cartCountBadge = document.getElementById("cart-count");

    addCartButtons.forEach(button => {
        button.addEventListener("click", function (e) {
            e.preventDefault();
            const libroId = this.getAttribute("data-id");

            // Simulación o petición asíncrona fetch al controlador backend
            fetch(`/carrito/agregar?libroId=${libroId}`, { method: 'POST' })
                .then(response => {
                    if (response.ok) {
                        let currentCount = parseInt(cartCountBadge.textContent) || 0;
                        cartCountBadge.textContent = currentCount + 1;
                        alert("¡Libro añadido al carrito exitosamente!");
                    }
                }).catch(err => console.error("Error al gestionar el carrito:", err));
        });
    });

    // Filtros dinámicos al marcar los checkboxes
    const checkboxes = document.querySelectorAll(".filter-checkbox");
    checkboxes.forEach(cb => {
        cb.addEventListener("change", function () {
            // Envía automáticamente el formulario de filtros sin recargar manualmente
            document.getElementById("filter-form").submit();
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    // Lógica para mostrar / ocultar contraseña en el Login
    const togglePassword = document.getElementById("togglePassword");
    const passwordInput = document.getElementById("password");

    if (togglePassword && passwordInput) {
        togglePassword.addEventListener("click", function () {
            // Cambiar el tipo de input
            const type = passwordInput.getAttribute("type") === "password" ? "text" : "password";
            passwordInput.setAttribute("type", type);
            
            // Cambiar el icono del ojo
            this.querySelector("i").classList.toggle("bi-eye");
            this.querySelector("i").classList.toggle("bi-eye-slash");
        });
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const radioRecojo = document.getElementById("entregaRecojo");
    const radioDelivery = document.getElementById("entregaDelivery");
    const sectionDireccion = document.getElementById("sectionDireccionEnvio");
    const txtCostoEnvio = document.getElementById("txtCostoEnvio");
    const txtTotalPedido = document.getElementById("txtTotalPedido");

    if (radioRecojo && radioDelivery && sectionDireccion) {
        const handleDeliveryToggle = () => {
            if (radioDelivery.checked) {
                sectionDireccion.classList.remove("d-none");
                if (txtCostoEnvio) txtCostoEnvio.innerText = "S/ 5.99";
                if (txtTotalPedido) txtTotalPedido.innerText = "S/ 106.29"; // Simulación de suma de delivery
            } else {
                sectionDireccion.classList.add("d-none");
                if (txtCostoEnvio) txtCostoEnvio.innerText = "S/ 0.00";
                if (txtTotalPedido) txtTotalPedido.innerText = "S/ 100.30";
            }
        };

        radioRecojo.addEventListener("change", handleDeliveryToggle);
        radioDelivery.addEventListener("change", handleDeliveryToggle);
    }
});

document.addEventListener("DOMContentLoaded", function () {
    // 1. Capturamos los elementos del Navbar
    const cartBadge = document.getElementById("cart-badge");
    const cartLink = document.getElementById("cart-link");

    // 2. Recuperamos la cantidad guardada en la sesión del navegador o empezamos en 0
    let cartCount = parseInt(localStorage.getItem("cartCount")) || 0;
    
    // Actualizamos el número visualmente al cargar cualquier página
    if (cartBadge) {
        cartBadge.innerText = cartCount;
    }

    // 3. Escuchar clics en los botones de "Comprar Ahora" o "Añadir al Carrito"
    // Busca cualquier elemento con la clase que usamos en el catálogo y detalle
    document.querySelectorAll(".btn-add-cart-detalle").forEach(button => {
        button.addEventListener("click", function (e) {
            // Incrementamos el contador
            cartCount++;
            localStorage.setItem("cartCount", cartCount);
            
            if (cartBadge) {
                cartBadge.innerText = cartCount;
            }
        });
    });

    // 4. CONTROL DE REDIRECCIÓN: Validar clic en el icono del carrito
    if (cartLink) {
        cartLink.addEventListener("click", function (e) {
            // Si el carrito está en 0, cancelamos el viaje a la pantalla de pago
            if (cartCount === 0) {
                e.preventDefault(); 
                alert("Tu carrito está vacío. ¡Añade un libro para continuar!");
            }
        });
    }
});