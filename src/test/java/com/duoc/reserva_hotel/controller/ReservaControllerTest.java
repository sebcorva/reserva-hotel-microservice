package com.duoc.reserva_hotel.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;

import com.duoc.reserva_hotel.dto.ReservaDto;
import com.duoc.reserva_hotel.dto.ReservaDtoUpdate;
import com.duoc.reserva_hotel.model.Reserva;
import com.duoc.reserva_hotel.service.ReservaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ReservaController.class)
public class ReservaControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaService reservaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reserva = new Reserva();
        reserva.setId(1L);
        reserva.setNombreHuesped("Sebastian Corvalan");
        reserva.setCantidadPersonas(2);
        reserva.setFechaInicio(LocalDate.of(2026, 5, 4));
        reserva.setFechaTermino(LocalDate.of(2026, 5, 10));
        reserva.setEstado("RESERVADO");
    }

    @Test
    void testGetAllReservas() throws Exception {
        Mockito.when(reservaService.getAllReservas()).thenReturn(Arrays.asList(reserva));

        mockMvc.perform(get("/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreHuesped").value("Sebastian Corvalan"))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void testIsAvailable() throws Exception {
        Mockito.when(reservaService.isAvailable(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(true);

        mockMvc.perform(get("/reservas/disponibilidad")
                .param("inicio", "2026-05-04")
                .param("fin", "2026-05-10"))
                .andExpect(status().isOk())
                .andExpect(content().string("Disponible"));
    }
    @Test
    void testCreateReserva() throws Exception {
        ReservaDto dto = new ReservaDto(
            "Sebastian Corvalan", 
            2, 
            LocalDate.of(2026, 5, 4), 
            LocalDate.of(2026, 5, 10)
        );

        Mockito.when(reservaService.createReserva(anyString(), anyInt(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(reserva);


        mockMvc.perform(post("/reservas/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreHuesped").value("Sebastian Corvalan"));
    }

    @Test
    void testUpdateReserva() throws Exception {
        ReservaDtoUpdate dtoUpdate = new ReservaDtoUpdate(
            "Sebastian Corvalan", 
            2, 
            LocalDate.of(2026, 5, 4), 
            LocalDate.of(2026, 5, 10),
            "CONFIRMADO"
        );

        Mockito.when(reservaService.updateReserva(eq(1L), anyString(), anyInt(), any(LocalDate.class), any(LocalDate.class), anyString()))
                .thenReturn(reserva);

        mockMvc.perform(put("/reservas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoUpdate))) 
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(reserva)));
    }

    @Test
    void testDeleteReserva() throws Exception {
        mockMvc.perform(delete("/reservas/1"))
                .andExpect(status().isOk());
        
        Mockito.verify(reservaService, Mockito.times(1)).deleteReserva(1L);
    }
}
