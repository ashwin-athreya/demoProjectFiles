package com.cg.mts.service;

import com.cg.mts.dto.TheatreDTO;
import com.cg.mts.exception.TheatreNotFoundException;

import java.util.List;

public interface TheatreService {
	public List<TheatreDTO> getAllTheatres() throws TheatreNotFoundException;

	public TheatreDTO findTheatres(int theatreId);

	public TheatreDTO addTheatre(TheatreDTO theatreDTO) throws TheatreNotFoundException;

	public List<TheatreDTO> updateTheatre(TheatreDTO theatreDTO) throws TheatreNotFoundException;

	public List<TheatreDTO> deleteTheatreById(int theatreId) throws TheatreNotFoundException;
	
	public List<TheatreDTO> findTheatresByMovie(Integer movieId) throws TheatreNotFoundException;
}
