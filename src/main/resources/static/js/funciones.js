document.addEventListener("DOMContentLoaded", function () {

    // ==========================================================================
    // 1. MÓDULO: LOGIN (Mostrar / Ocultar Contraseña)
    // ==========================================================================
    const togglePassword = document.getElementById("togglePassword");
    const passwordInput = document.getElementById("password");

    if (togglePassword && passwordInput) {
        togglePassword.addEventListener("click", function () {
            const type = passwordInput.getAttribute("type") === "password" ? "text" : "password";
            passwordInput.setAttribute("type", type);
            
            this.querySelector("i").classList.toggle("bi-eye");
            this.querySelector("i").classList.toggle("bi-eye-slash");
        });
    }

    // ==========================================================================
    // 2. MÓDULO: CHECKOUT (Lógica de Delivery vs Recojo)
    // ==========================================================================
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
                if (txtTotalPedido) txtTotalPedido.innerText = "S/ 106.29"; // Suma simulada
            } else {
                sectionDireccion.classList.add("d-none");
                if (txtCostoEnvio) txtCostoEnvio.innerText = "S/ 0.00";
                if (txtTotalPedido) txtTotalPedido.innerText = "S/ 100.30";
            }
        };

        radioRecojo.addEventListener("change", handleDeliveryToggle);
        radioDelivery.addEventListener("change", handleDeliveryToggle);
    }

    // ==========================================================================
    // 3. MÓDULO: CARRITO (Simulación Front-end con LocalStorage)
    // ==========================================================================
    const cartBadge = document.getElementById("cart-badge");
    const cartLink = document.getElementById("cart-link");
    let cartCount = parseInt(localStorage.getItem("cartCount")) || 0;
    
    // Actualizar visualmente al cargar
    if (cartBadge) {
        cartBadge.innerText = cartCount;
    }

    // Sumar al carrito en Catálogo y Detalle
    document.querySelectorAll(".btn-add-cart-detalle, .btn-add-cart").forEach(button => {
        button.addEventListener("click", function (e) {
            e.preventDefault(); // Evita que la página salte
            cartCount++;
            localStorage.setItem("cartCount", cartCount);
            
            if (cartBadge) {
                cartBadge.innerText = cartCount;
                // Pequeña animación visual para feedback
                cartBadge.classList.add("scale-up");
                setTimeout(() => cartBadge.classList.remove("scale-up"), 200);
            }
            alert("¡Libro añadido al carrito exitosamente!");
        });
    });

    // Validar clic en el icono del carrito
    if (cartLink) {
        cartLink.addEventListener("click", function (e) {
            if (cartCount === 0) {
                e.preventDefault(); 
                alert("Tu carrito está vacío. ¡Añade un libro para continuar!");
            }
        });
    }

    // ==========================================================================
    // 4. MÓDULO: MI PERFIL (Tabs y Modal de Renovación)
    // ==========================================================================
    const tabs = document.querySelectorAll("#profileTabs .list-group-item");
    const contents = document.querySelectorAll(".nav-tab-content");

    if (tabs.length > 0 && contents.length > 0) {
        tabs.forEach(tab => {
            tab.addEventListener("click", function() {
                tabs.forEach(t => t.classList.remove("active"));
                contents.forEach(c => c.classList.remove("active"));

                this.classList.add("active");
                const targetTab = this.getAttribute("data-tab");
                document.getElementById(targetTab).classList.add("active");
            });
        });
    }

    const botonesRenovar = document.querySelectorAll(".btn-renovar");
    const inputModalId = document.getElementById("modalPrestamoId");

    if (botonesRenovar.length > 0 && inputModalId) {
        botonesRenovar.forEach(boton => {
            boton.addEventListener("click", function() {
                inputModalId.value = this.getAttribute("data-id");
            });
        });
    }

    // ==========================================================================
    // 5. MÓDULO: CATÁLOGO (Envío automático de filtros)
    // ==========================================================================
    const checkboxes = document.querySelectorAll(".filter-checkbox");
    const filterForm = document.getElementById("filter-form");
    
    if (checkboxes.length > 0 && filterForm) {
        checkboxes.forEach(cb => {
            cb.addEventListener("change", function () {
                filterForm.submit();
            });
        });
    }

});

// Control de redirección de pestañas desde el footer al perfil
document.addEventListener("DOMContentLoaded", function () {
    const rutas = {
        '#pedidos-tab': 'tab-pedidos',
        '#prestamos-tab': 'tab-prestamos',
        '#multas-tab': 'tab-multas',
        '#ajustes-tab': 'tab-ajustes'
    };

    const hash = window.location.hash;
    
    if (hash && rutas[hash]) {
        const targetDataTab = rutas[hash];
        const botonObjetivo = document.querySelector(`button[data-tab="${targetDataTab}"]`);

        if (botonObjetivo) {
            // 1. Simula un clic real en el botón del menú lateral
            // Esto es genial porque activa automáticamente toda la lógica de cambio de panel que ya tenías programada
            botonObjetivo.click();
        }
    }
});

// Manejo interactivo de votación por estrellas en reseñas
document.addEventListener("DOMContentLoaded", function () {
    const estrellas = document.querySelectorAll(".estrella-voto");
    const inputCalificacion = document.getElementById("calificacionInput");

    if (estrellas.length > 0 && inputCalificacion) {
        estrellas.forEach((estrella) => {
            
            estrella.addEventListener("mouseover", function () {
                const valor = parseInt(this.getAttribute("data-value"));
                estrellas.forEach(e => e.classList.remove("hovered"));
                for (let i = 0; i < valor; i++) {
                    estrellas[i].classList.add("hovered");
                }
            });

            estrella.addEventListener("mouseout", function () {
                estrellas.forEach(e => e.classList.remove("hovered"));
            });

            estrella.addEventListener("click", function () {
                const valor = parseInt(this.getAttribute("data-value"));
                inputCalificacion.value = valor;
                
                estrellas.forEach(e => e.classList.remove("selected"));
                for (let i = 0; i < valor; i++) {
                    estrellas[i].classList.add("selected");
                }
            });
        });
    }
});

// Control dinámico de Tipo de Entrega en el Checkout
document.addEventListener("DOMContentLoaded", function () {
    const radioRecojo = document.getElementById("entregaRecojo");
    const radioDelivery = document.getElementById("entregaDelivery");
    const sectionDireccion = document.getElementById("sectionDireccionEnvio");

    if (radioRecojo && radioDelivery && sectionDireccion) {
        // Escucha cambios en la opción de Recojo
        radioRecojo.addEventListener("change", function () {
            if (this.checked) {
                sectionDireccion.classList.add("d-none"); // Oculta dirección
            }
        });

        // Escucha cambios en la opción de Delivery
        radioDelivery.addEventListener("change", function () {
            if (this.checked) {
                sectionDireccion.classList.remove("d-none"); // Muestra dirección
            }
        });
    }
});

