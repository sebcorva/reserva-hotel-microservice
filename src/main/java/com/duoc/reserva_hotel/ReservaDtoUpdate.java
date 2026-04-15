package com.duoc.reserva_hotel;

public record ReservaDtoUpdate(
    String nombreHuesped,
    Integer personas,
    String inicio,
    String fin,
    String estado
) {}
