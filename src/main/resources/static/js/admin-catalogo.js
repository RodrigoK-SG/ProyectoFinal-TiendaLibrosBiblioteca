document.addEventListener('DOMContentLoaded', function() {
    
    // --- LÓGICA MODAL EDITAR LIBRO ---
    const modalEditar = document.getElementById('modalEditarLibro');
    if (modalEditar) {
        modalEditar.addEventListener('show.bs.modal', function (event) {
            // Botón que activó el modal (el ícono del lápiz)
            const button = event.relatedTarget;
            
            // Extraer info de los atributos data-*
            const id = button.getAttribute('data-id');
            const isbn = button.getAttribute('data-isbn');
            const titulo = button.getAttribute('data-titulo');
            const formato = button.getAttribute('data-formato');
            const pVenta = button.getAttribute('data-pventa');
            const pAlquiler = button.getAttribute('data-palquiler');
            const activo = button.getAttribute('data-activo') === 'true';

            // Actualizar los campos del formulario
            modalEditar.querySelector('#editId').value = id;
            modalEditar.querySelector('#editIsbn').value = isbn;
            modalEditar.querySelector('#editTitulo').value = titulo;
            modalEditar.querySelector('#editFormato').value = formato;
            modalEditar.querySelector('#editPrecioVenta').value = pVenta;
            modalEditar.querySelector('#editPrecioAlquiler').value = pAlquiler;
            
            // Switch de estado
            const switchEstado = modalEditar.querySelector('#editSwitchEstado');
            const labelEstado = modalEditar.querySelector('#labelEstadoTexto');
            switchEstado.checked = activo;
            labelEstado.textContent = activo ? 'Estado del Libro: ACTIVO' : 'Estado del Libro: INACTIVO';
            labelEstado.className = activo ? 'form-check-label fw-bold text-success' : 'form-check-label fw-bold text-danger';
        });

        // Evento para cambiar el texto del switch dinámicamente cuando el usuario lo presiona
        const switchBtn = document.getElementById('editSwitchEstado');
        if(switchBtn) {
            switchBtn.addEventListener('change', function() {
                const label = document.getElementById('labelEstadoTexto');
                if(this.checked) {
                    label.textContent = 'Estado del Libro: ACTIVO';
                    label.className = 'form-check-label fw-bold text-success';
                } else {
                    label.textContent = 'Estado del Libro: INACTIVO';
                    label.className = 'form-check-label fw-bold text-danger';
                }
            });
        }
    }

    // --- LÓGICA MODAL DESACTIVAR LIBRO ---
    const modalDesactivar = document.getElementById('modalDesactivarLibro');
    if (modalDesactivar) {
        modalDesactivar.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const id = button.getAttribute('data-id');
            const titulo = button.getAttribute('data-titulo');

            // Insertar datos en el modal
            modalDesactivar.querySelector('#deleteId').value = id;
            modalDesactivar.querySelector('#deleteTitulo').textContent = titulo;
        });
    }
});