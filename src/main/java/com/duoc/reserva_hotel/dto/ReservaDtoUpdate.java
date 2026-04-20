package com.duoc.reserva_hotel.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.*;

public record ReservaDtoUpdate(
    @NotBlank(message = "Debe ingresar un nombre válido")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo debe contener letras")
    String nombreHuesped,

    @Min(value = 0, message = "La cantidad de personas no puede ser negativa")
    @Max(value = 10, message = "No puede ingresar más de 10 huesped")
    @NotNull(message = "Debe ingresar una cantidad")
    Integer personas,

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de reserva debe ser hoy o una fecha futura")
    LocalDate inicio,

    @NotNull(message = "La fecha de termino es obligatoria")
    @Future(message = "La fecha de termino debe ser una fecha futura")
    LocalDate fin,

    @NotBlank
    String estado
) {}
