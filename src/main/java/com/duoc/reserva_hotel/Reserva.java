package com.duoc.reserva_hotel;

import java.time.LocalDate;

import lombok.*; 

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    private Long id;
    private String numeroReserva;
    private String nombreHuesped;
    private Number cantidadPersonas;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private String estado;
}
