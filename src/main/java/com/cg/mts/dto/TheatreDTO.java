package com.cg.mts.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheatreDTO {
    private int theatreId;
    private String theatreName;
    private String theatreCity;
    private String managerName;
    private String managerContact;
    private List<ScreenDTO> screens;
    private List<ShowDTO> shows;
    
}