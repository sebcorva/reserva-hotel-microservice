package com.duoc.reserva_hotel.dto;

public record ReservaDtoUpdate(
    String nombreHuesped,
    Integer personas,
    String inicio,
    String fin,
    String estado
) {}
