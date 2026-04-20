package com.duoc.reserva_hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.reserva_hotel.model.Reserva;

@Repository
public interface ReservaRepository  extends JpaRepository<Reserva, Long>{
    
}
