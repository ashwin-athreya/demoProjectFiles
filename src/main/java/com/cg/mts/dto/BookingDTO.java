package com.cg.mts.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private int transactionId;
    private ShowDTO show;
    private LocalDate bookingDate;
    private String transactionMode;
    private String transactionStatus;
    private double totalCost;
    private CustomerDTO customer;
    private TicketDTO ticket;
  
}