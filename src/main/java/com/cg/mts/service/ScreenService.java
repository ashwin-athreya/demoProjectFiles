package com.cg.mts.service;

import java.util.List;

import com.cg.mts.dto.ScreenDTO;
import com.cg.mts.dto.TheatreDTO;
import com.cg.mts.exception.ScreenNotFoundException;


public interface ScreenService {
	public ScreenDTO addScreen(ScreenDTO screenDTO, Integer theatreId) throws ScreenNotFoundException;
	public List<ScreenDTO> viewScreenList() throws ScreenNotFoundException;
	public ScreenDTO updateScreen(ScreenDTO screenDTO, Integer theatreId) throws ScreenNotFoundException;
	public ScreenDTO viewScreen(int screenId) throws ScreenNotFoundException;
	public TheatreDTO getTheatre(int screenId) throws ScreenNotFoundException;
}
