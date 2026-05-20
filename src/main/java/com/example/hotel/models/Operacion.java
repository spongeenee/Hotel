package com.example.hotel.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Operacion(long ID, long responsableID, long reservacionID, Accion accion, LocalDateTime hora, String motivo) {
}
