package com.cg.mts.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private int ticketId;
    private int noOfSeats;
    private boolean ticketStatus;
    private List<SeatDTO> seats;
    private BookingDTO booking;
    
}