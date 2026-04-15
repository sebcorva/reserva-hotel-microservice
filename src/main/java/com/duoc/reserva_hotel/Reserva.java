package com.duoc.reserva_hotel;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*; 

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroReserva;
    private String nombreHuesped;
    private Integer cantidadPersonas;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private String estado;
}
