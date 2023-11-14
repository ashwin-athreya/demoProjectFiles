package com.cg.mts.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenDTO {
    private int screenId;
    private String screenName;
    private int rows;
    private int columns;
    private TheatreDTO theatre;
    private List<ShowDTO> shows;
   
}