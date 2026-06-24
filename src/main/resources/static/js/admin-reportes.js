document.addEventListener('DOMContentLoaded', function() {
    
    // 1. Gráfico de Barras: Ventas Físicas vs Online
    const ctxBar = document.getElementById('ventasChart').getContext('2d');
    new Chart(ctxBar, {
        type: 'bar',
        data: {
            labels: ['Ene', 'Feb', 'Mar', 'Abr', 'May'],
            datasets: [
                {
                    label: 'Ventas Físicas',
                    data: [4000, 3000, 2000, 2800, 16000], // Valores aproximados de tu diseño
                    backgroundColor: '#3b82f6', // Azul
                    borderRadius: 4,
                    barPercentage: 0.6,
                    categoryPercentage: 0.8
                },
                {
                    label: 'Ventas Online',
                    data: [2500, 1500, 10000, 4000, 5000], // Valores aproximados de tu diseño
                    backgroundColor: '#10b981', // Verde
                    borderRadius: 4,
                    barPercentage: 0.6,
                    categoryPercentage: 0.8
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'bottom',
                    labels: { usePointStyle: true, boxWidth: 8, padding: 20 }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    max: 16000,
                    ticks: {
                        stepSize: 4000,
                        callback: function(value) {
                            return 'S/ ' + value;
                        }
                    },
                    border: { display: false },
                    grid: { color: '#f8f9fa', drawBorder: false }
                },
                x: {
                    grid: { display: false },
                    border: { display: false }
                }
            }
        }
    });

    // 2. Gráfico de Dona: Top 5 Libros Más Alquilados
    const ctxDoughnut = document.getElementById('topLibrosChart').getContext('2d');
    new Chart(ctxDoughnut, {
        type: 'doughnut',
        data: {
            labels: ['DUNE 37%', '1984 23%', 'Sapiens 16%', 'Cosmos 13%', 'Hábitos Atómicos 10%'],
            datasets: [{
                data: [37, 23, 16, 13, 10],
                // El diseño usa un color uniforme oscuro con bordes blancos gruesos
                backgroundColor: ['#475569', '#475569', '#475569', '#475569', '#475569'],
                borderWidth: 4,
                borderColor: '#ffffff',
                hoverOffset: 4
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            cutout: '75%', // Define el grosor de la dona
            plugins: {
                legend: {
                    position: 'right', // Posiciona las etiquetas alrededor como en tu imagen
                    display: false // Ocultamos la leyenda por defecto para personalizar las etiquetas si se requiere, pero activarla es más fácil.
                },
                // Habilitar tooltips
                tooltip: { enabled: true }
            },
            layout: {
                padding: 20
            }
        },
        plugins: [{
            // Plugin personalizado para dibujar las líneas y textos alrededor de la dona (Opcional, versión simplificada)
            id: 'customLabels',
            afterDraw(chart, args, options) {
                // En un proyecto real, se usan librerías como chartjs-plugin-datalabels 
                // Aquí delegamos los labels al tooltip o a la leyenda para mantenerlo limpio.
            }
        }]
    });
});