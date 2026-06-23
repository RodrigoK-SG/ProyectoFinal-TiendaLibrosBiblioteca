document.addEventListener('DOMContentLoaded', function() {
    
    // LÓGICA DE CANTIDAD
    const btnRestar = document.getElementById('btnRestar');
    const btnSumar = document.getElementById('btnSumar');
    const inputCantidad = document.getElementById('inputCantidad');

    if (btnSumar && btnRestar && inputCantidad) {
        btnSumar.addEventListener('click', () => {
            let actual = parseInt(inputCantidad.value) || 0;
            inputCantidad.value = actual + 1;
        });

        btnRestar.addEventListener('click', () => {
            let actual = parseInt(inputCantidad.value) || 0;
            if(actual > 1) {
                inputCantidad.value = actual - 1;
            }
        });
    }

    // LÓGICA DE MOSTRAR/OCULTAR ZONAS EN EL MODAL
    const selectTipoMovimiento = document.getElementById('selectTipoMovimiento');
    const zonaLibroExistente = document.getElementById('zonaLibroExistente');
    const zonaLibroNuevo = document.getElementById('zonaLibroNuevo');
    const selectLibro = document.getElementById('selectLibro');
    const inputNuevoIsbn = document.getElementById('inputNuevoIsbn');

    if (selectTipoMovimiento) {
        selectTipoMovimiento.addEventListener('change', function() {
            const tipo = this.value;

            if (tipo === 'INGRESO_PROVEEDOR') {
                zonaLibroExistente.style.display = 'none';
                selectLibro.removeAttribute('required');
                selectLibro.value = ''; 
                
                zonaLibroNuevo.style.display = 'block';
                inputNuevoIsbn.setAttribute('required', 'required');
            } else {
                zonaLibroExistente.style.display = 'block';
                selectLibro.setAttribute('required', 'required');
                
                zonaLibroNuevo.style.display = 'none';
                inputNuevoIsbn.removeAttribute('required');
                inputNuevoIsbn.value = ''; 
            }
        });
    }
});