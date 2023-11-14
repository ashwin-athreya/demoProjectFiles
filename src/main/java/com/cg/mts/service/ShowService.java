package com.cg.mts.service;

import java.time.LocalDate;
import java.util.List;

import com.cg.mts.dto.ShowDTO;

public interface ShowService {

	public ShowDTO addShow(ShowDTO showDTO, Integer theatreId, Integer screenId);

	public ShowDTO updateShow(ShowDTO showDTO, Integer theatreId, Integer screenId);

	public ShowDTO removeShow(int showid);

	public ShowDTO viewShow(int showid);

	public List<ShowDTO> viewAllShows();

	public List<ShowDTO> viewShowList(int theatreid);

	public List<ShowDTO> viewShowList(LocalDate date);

}
