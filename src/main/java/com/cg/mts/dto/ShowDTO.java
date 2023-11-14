package com.cg.mts.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowDTO {
    private int showId;
    private LocalDateTime showStartTime;
    private LocalDateTime showEndTime;
    private String showName;
    private MovieDTO movie;
    private ScreenDTO screen;
    private TheatreDTO theatre;
    private BookingDTO booking;
    private LocalDate showDate;
    
   
}