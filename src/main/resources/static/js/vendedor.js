document.addEventListener("DOMContentLoaded", function() {
    
    // 1. Resaltar pestaña activa del navbar
    const currentUrl = window.location.pathname;
    const navCaja = document.getElementById("nav-caja");
    const navCotizaciones = document.getElementById("nav-cotizaciones");
    if (navCaja && navCotizaciones) {
        if (currentUrl.includes("/vendedor/caja")) {
            navCaja.classList.add("bg-danger", "text-white");
            navCaja.classList.remove("text-dark");
        } else if (currentUrl.includes("/vendedor/cotizaciones")) {
            navCotizaciones.classList.add("bg-danger", "text-white");
            navCotizaciones.classList.remove("text-secondary");
        }
    }

    // 2. Buscador en tiempo real de la grilla
    const inputBuscar = document.querySelector("input[placeholder*='Buscar libro']");
    if (inputBuscar) {
        inputBuscar.addEventListener("input", function(e) {
            const texto = e.target.value.toLowerCase().trim();
            document.querySelectorAll(".libro-card").forEach(tarjeta => {
                const titulo = tarjeta.querySelector("h6").textContent.toLowerCase();
                const columna = tarjeta.closest(".col");
                columna.style.display = titulo.includes(texto) ? "block" : "none";
            });
        });
    }

    // ========================================================
    // 3. LÓGICA INTERACTIVA DEL PUNTO DE VENTA (CAJA)
    // ========================================================
    let carrito = [];
    const contenedorDetalle = document.querySelector(".flex-grow-1.overflow-auto.pe-2.mb-4");

    // Escuchar clics en las tarjetas de libros
    document.querySelectorAll(".libro-card").forEach(tarjeta => {
        tarjeta.addEventListener("click", function() {
            const titulo = tarjeta.querySelector("h6").textContent;
            const precio = parseFloat(tarjeta.querySelector(".fs-5").textContent.replace("S/ ", ""));
            const imagen = tarjeta.querySelector("img").getAttribute("src");

            agregarAlCarrito(titulo, precio, imagen);
        });
    });

    function agregarAlCarrito(titulo, precio, imagen) {
        // Verificar si el libro ya está en el detalle
        const itemExistente = carrito.find(item => item.titulo === titulo);

        if (itemExistente) {
            itemExistente.cantidad++;
        } else {
            carrito.push({ titulo, precio, imagen, cantidad: 1 });
        }
        renderizarCarrito();
    }

    function renderizarCarrito() {
        if (!contenedorDetalle) return;
        contenedorDetalle.innerHTML = "";

        if (carrito.length === 0) {
            contenedorDetalle.innerHTML = `<p class="text-muted text-center py-4 small">Selecciona libros de la izquierda para vender</p>`;
            actualizarTotales(0);
            return;
        }

        carrito.forEach((item, index) => {
            const htmlItem = `
                <div class="d-flex align-items-center mb-3 p-2 bg-light rounded-3 position-relative">
                    <img src="${item.imagen}" class="rounded me-3 object-fit-cover" width="45" height="60" alt="Libro">
                    <div class="flex-grow-1">
                        <h6 class="fw-bold mb-0 small">${item.titulo}</h6>
                        <div class="d-flex align-items-center mt-2">
                            <div class="btn-group btn-group-sm" role="group">
                                <button type="button" class="btn btn-outline-secondary px-2 btn-menos" data-index="${index}">-</button>
                                <span class="btn border-secondary bg-white px-3 fw-bold disabled text-dark">${item.cantidad}</span>
                                <button type="button" class="btn btn-outline-secondary px-2 btn-mas" data-index="${index}">+</button>
                            </div>
                        </div>
                    </div>
                    <div class="text-end ms-3">
                        <button class="btn btn-link text-danger p-0 mb-1 btn-eliminar" data-index="${index}"><i class="bi bi-trash"></i></button>
                        <p class="fw-bold mb-0 text-dark">S/ ${(item.precio * item.cantidad).toFixed(2)}</p>
                    </div>
                </div>
            `;
            contenedorDetalle.insertAdjacentHTML("beforeend", htmlItem);
        });

        // Eventos de los botones dentro del detalle
        document.querySelectorAll(".btn-mas").forEach(b => b.addEventListener("click", (e) => { carrito[e.target.dataset.index].cantidad++; renderizarCarrito(); }));
        document.querySelectorAll(".btn-menos").forEach(b => b.addEventListener("click", (e) => { 
            if(carrito[e.target.dataset.index].cantidad > 1) carrito[e.target.dataset.index].cantidad--;
            renderizarCarrito(); 
        }));
        document.querySelectorAll(".btn-eliminar").forEach(b => b.closest(".btn-eliminar").addEventListener("click", (e) => {
            const idx = e.currentTarget.dataset.index;
            carrito.splice(idx, 1);
            renderizarCarrito();
        }));

        // Calcular montos totales
        const totalVenta = carrito.reduce((sum, item) => sum + (item.precio * item.cantidad), 0);
        actualizarTotales(totalVenta);
    }

    function actualizarTotales(total) {
        const subtotal = total / 1.18;
        const igv = total - subtotal;

        // Inyectar en los campos correspondientes del HTML
        const txtSubtotal = document.querySelector(".d-flex.justify-content-between.mb-1.small.text-muted span:last-child");
        const txtIgv = document.querySelector(".d-flex.justify-content-between.mb-3.small.text-muted span:last-child");
        const txtTotal = document.querySelector("h3.fw-bold.mb-0.text-dark");
        const btnCobrar = document.querySelector("button.btn-danger.w-100.py-3");

        if(txtSubtotal) txtSubtotal.textContent = `S/ ${subtotal.toFixed(2)}`;
        if(txtIgv) txtIgv.textContent = `S/ ${igv.toFixed(2)}`;
        if(txtTotal) txtTotal.textContent = `S/ ${total.toFixed(2)}`;
        if(btnCobrar) btnCobrar.textContent = `COBRAR S/ ${total.toFixed(2)}`;
    }

    // Iniciar vacío
    renderizarCarrito();
});