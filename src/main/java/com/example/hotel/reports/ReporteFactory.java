package com.example.hotel.reports;

public class ReporteFactory {

    public static ReporteStrategy crear(String tipo) {
        return switch (tipo) {
            case "PDF" -> new PDFStrategy();
            case "EXCEL" -> new ExcelStrategy();
            default -> throw new IllegalArgumentException("Tipo de reporte no soportado: " + tipo);
        };
    }
}
