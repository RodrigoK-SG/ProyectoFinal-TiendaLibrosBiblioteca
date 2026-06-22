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