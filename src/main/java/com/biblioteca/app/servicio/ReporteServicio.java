package com.biblioteca.app.servicio;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.biblioteca.app.modelo.enums.CanalVenta;
import com.biblioteca.app.repositorio.*;

@Service
@RequiredArgsConstructor
public class ReporteServicio {

    private final PedidoRepositorio pedidoRepository;
    private final DetallePedidoRepositorio detallePedidoRepository;
    private final PrestamoRepositorio prestamoRepository;

    public Map<String, Object> generarReporteMensual(String anioMes) {
        Map<String, Object> reporte = new HashMap<>();
        
        // 1. Determinar el rango de fechas a partir del String "YYYY-MM"
        LocalDate fechaSeleccionada = (anioMes != null && !anioMes.isEmpty()) 
                ? LocalDate.parse(anioMes + "-01") 
                : LocalDate.now().withDayOfMonth(1);
                
        LocalDateTime inicioMes = fechaSeleccionada.atStartOfDay();
        LocalDateTime finMes = fechaSeleccionada.withDayOfMonth(fechaSeleccionada.lengthOfMonth()).atTime(LocalTime.MAX);

        // 2. KPIs Superiores
        reporte.put("ingresosTotales", pedidoRepository.sumarIngresosPorFecha(inicioMes, finMes));
        reporte.put("librosVendidos", detallePedidoRepository.sumarLibrosVendidosPorFecha(inicioMes, finMes));
        reporte.put("prestamosActivos", prestamoRepository.contarPrestamosActivos()); // Esto suele ser histórico (actual), no filtrado por mes

        // Formato para la vista (Ej: "Mayo 2026")
        String nombreMes = fechaSeleccionada.format(DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("es", "ES")));
        reporte.put("mesActualTexto", nombreMes.substring(0, 1).toUpperCase() + nombreMes.substring(1));
        reporte.put("mesActualInput", fechaSeleccionada.format(DateTimeFormatter.ofPattern("yyyy-MM")));

        // 3. Gráfico de Dona: Top 5 Libros
        List<Object[]> top5Raw = prestamoRepository.topLibrosAlquilados(inicioMes, finMes);
        List<String> topLabels = new ArrayList<>();
        List<Long> topData = new ArrayList<>();
        
        for (Object[] fila : top5Raw) {
            topLabels.add((String) fila[0]);
            topData.add((Long) fila[1]);
        }
        reporte.put("topLibrosLabels", topLabels);
        reporte.put("topLibrosData", topData);

        // 4. Gráfico de Barras: Últimos 5 meses (incluyendo el seleccionado)
        List<String> chartLabels = new ArrayList<>();
        List<BigDecimal> chartFisico = new ArrayList<>();
        List<BigDecimal> chartOnline = new ArrayList<>();

        for (int i = 4; i >= 0; i--) {
            LocalDate mesIteracion = fechaSeleccionada.minusMonths(i);
            LocalDateTime inicioIter = mesIteracion.withDayOfMonth(1).atStartOfDay();
            LocalDateTime finIter = mesIteracion.withDayOfMonth(mesIteracion.lengthOfMonth()).atTime(LocalTime.MAX);
            
            // Etiqueta del eje X (Ej: "Ene", "Feb")
            chartLabels.add(mesIteracion.format(DateTimeFormatter.ofPattern("MMM", new Locale("es", "ES"))).toUpperCase());
            
            chartFisico.add(pedidoRepository.sumarIngresosPorCanalYFecha(CanalVenta.valueOf("FISICO"), inicioIter, finIter));
            chartOnline.add(pedidoRepository.sumarIngresosPorCanalYFecha(CanalVenta.valueOf("ONLINE"), inicioIter, finIter));
        }

        reporte.put("chartLabels", chartLabels);
        reporte.put("chartFisicas", chartFisico);
        reporte.put("chartOnline", chartOnline);

        return reporte;
    }
}