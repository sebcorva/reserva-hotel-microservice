package com.duoc.reserva_hotel.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_reserva")
    private String numeroReserva;

    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo debe contener letras")
    @NotBlank(message = "Debe ingresar un nombre valido")
    @Column(name = "nombre_huesped")
    private String nombreHuesped;

    @Min(value = 0, message = "La cantidad de personas no puede ser negativa")
    @Max(value = 10, message = "No puede ingresar más de 10 huesped")
    @NotNull(message = "Debe ingresar una cantidad")
    @Column(name = "cantidad_personas")
    private Integer cantidadPersonas;
    
    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de reserva debe ser hoy o una fecha futura")
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de termino es obligatoria")
    @Future(message = "La fecha de termino debe ser una fecha futura")
    @Column(name = "fecha_termino")
    private LocalDate fechaTermino;
    
    @NotBlank
    @Pattern(regexp = "^(RESERVADO|CONFIRMADO|PAGADO)$", message = "El estado solo puede ser RESERVADO, CONFIRMADO o PAGADO")
    @Column(name = "estado")
    private String estado;
}
