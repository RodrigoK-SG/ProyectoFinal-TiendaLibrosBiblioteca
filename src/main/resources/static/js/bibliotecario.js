// --- LÓGICA DE RESERVAS ---

    // Mostrar Toast de Notificación
    const btnNotificadores = document.querySelectorAll('.btn-notificar');
    const toastNotificacion = document.getElementById('toastNotificacion');
    
    if (toastNotificacion) {
        const toast = new bootstrap.Toast(toastNotificacion);
        btnNotificadores.forEach(btn => {
            btn.addEventListener('click', function(e) {
                // En la vida real, aquí harías un fetch() al controlador o enviarías el form
                // form.submit();
                // Por ahora, solo mostramos el componente visual del wireframe
                toast.show();
            });
        });
    }

    // Modal Confirmar Entrega
    const modalEnt = document.getElementById('modalEntrega');
    if (modalEnt) {
        modalEnt.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            document.getElementById('inputEntReservaId').value = button.getAttribute('data-id');
        });
    }

    // Modal Ver Detalles
    const modalDet = document.getElementById('modalDetalleReserva');
    if (modalDet) {
        modalDet.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            
            document.getElementById('detRef').textContent = "N° Ref: " + button.getAttribute('data-ref');
            document.getElementById('detLibro').textContent = button.getAttribute('data-libro');
            document.getElementById('detIsbn').textContent = "ISBN: " + button.getAttribute('data-isbn');
            document.getElementById('detCliente').textContent = button.getAttribute('data-cliente');
            document.getElementById('detDni').textContent = button.getAttribute('data-dni');
            document.getElementById('detEmail').textContent = button.getAttribute('data-email');
            document.getElementById('detTel').textContent = button.getAttribute('data-tel');
            document.getElementById('detFecha').textContent = button.getAttribute('data-fecha');
            
            const badgeEstado = document.getElementById('detEstado');
            const estado = button.getAttribute('data-estado');
            badgeEstado.textContent = estado;
            
            // Limpiar clases previas y poner la correcta según el estado
            badgeEstado.className = "badge-estado";
            if(estado === 'EN ESPERA') badgeEstado.classList.add('bg-espera');
            else if(estado === 'NOTIFICADO') badgeEstado.classList.add('bg-notificado');
            else if(estado === 'CANCELADO') badgeEstado.classList.add('bg-cancelado');
            else badgeEstado.classList.add('bg-green'); // Completado
        });
    }
	
	
	// --- LÓGICA DE PENALIZACIONES ---

	    // Modal Confirmar Pago
	    const modalPago = document.getElementById('modalPagoMulta');
	    if (modalPago) {
	        modalPago.addEventListener('show.bs.modal', function (event) {
	            const button = event.relatedTarget;
	            document.getElementById('inputPagoPenalizacionId').value = button.getAttribute('data-id');
	            document.getElementById('txtPagoMonto').textContent = "S/ " + button.getAttribute('data-monto');
	            document.getElementById('txtPagoMotivo').textContent = button.getAttribute('data-motivo');
	        });
	    }

	    // Modal Comprobante
	    const modalComp = document.getElementById('modalComprobante');
	    if (modalComp) {
	        modalComp.addEventListener('show.bs.modal', function (event) {
	            const button = event.relatedTarget;
	            document.getElementById('compOperacion').textContent = button.getAttribute('data-operacion');
	            document.getElementById('compCliente').textContent = button.getAttribute('data-cliente');
	            document.getElementById('compMotivo').textContent = button.getAttribute('data-motivo');
	            document.getElementById('compMonto').textContent = button.getAttribute('data-monto');
	            document.getElementById('compFecha').textContent = button.getAttribute('data-fecha');
	        });
	    }