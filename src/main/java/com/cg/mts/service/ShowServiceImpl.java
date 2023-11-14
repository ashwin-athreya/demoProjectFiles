package com.cg.mts.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.mts.dto.ShowDTO;
import com.cg.mts.entity.Screen;
import com.cg.mts.entity.Show;
import com.cg.mts.entity.Theatre;
import com.cg.mts.repository.ScreenRepository;
import com.cg.mts.repository.ShowRepository;
import com.cg.mts.repository.TheatreRepository;


@Service
public class ShowServiceImpl implements ShowService {
	@Autowired
	private ShowRepository showRepository;
	@Autowired
	private TheatreRepository theatreRepository;
	@Autowired
	private ScreenRepository screenRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ShowDTO addShow(ShowDTO showDTO, Integer theatreId, Integer screenId) {
		Show show = modelMapper.map(showDTO, Show.class);
        Theatre theatre = new Theatre();
        Screen screen = new Screen();
        if (theatreId != null) {
            theatre = theatreRepository.getOne(theatreId);
            show.setTheatre(theatre);
        }
        if (screenId != null) {
            screen = screenRepository.getOne(screenId);
            show.setScreen(screen);
        }
        showRepository.save(show);
        return modelMapper.map(show, ShowDTO.class);
	}

	
	@Override
	public ShowDTO updateShow(ShowDTO showDTO, Integer theatreId, Integer screenId) {
		Show existingShow = showRepository.findById(showDTO.getShowId()).orElse(null);
        if (existingShow != null) {
            existingShow = modelMapper.map(showDTO, Show.class);

            Theatre theatre = new Theatre();
            Screen screen = new Screen();

            if (theatreId != null) {
                theatre = theatreRepository.getOne(theatreId);
                existingShow.setTheatre(theatre);
            }

            if (screenId != null) {
                screen = screenRepository.getOne(screenId);
                existingShow.setScreen(screen);
            }

            showRepository.saveAndFlush(existingShow);
            return modelMapper.map(existingShow, ShowDTO.class);
        }
        return null;
	}

	
	@Override
	public ShowDTO removeShow(int showid) {
		Show show = showRepository.findById(showid).orElse(null);
        if (show != null) {
            showRepository.delete(show);
            return modelMapper.map(show, ShowDTO.class);
        }
        return null;
	}

	
	@Override
	public ShowDTO viewShow(int showid) {
		Show show = showRepository.findById(showid).orElse(null);
        return (show != null) ? modelMapper.map(show, ShowDTO.class) : null;
	}

	
	@Override
	public List<ShowDTO> viewAllShows() {
		 List<Show> shows = showRepository.findAll();
	        List<ShowDTO> showDTOs = new ArrayList<>();
	        for (Show show : shows) {
	            showDTOs.add(modelMapper.map(show, ShowDTO.class));
	        }
	        return showDTOs;
	}

	
	@Override
	public List<ShowDTO> viewShowList(int theatreid) {
		List<Show> shows = showRepository.getAllByTheatreId(theatreid);
        List<ShowDTO> showDTOs = new ArrayList<>();
        for (Show show : shows) {
            showDTOs.add(modelMapper.map(show, ShowDTO.class));
        }
        return showDTOs;
	}

	
	@Override
	public List<ShowDTO> viewShowList(LocalDate date) {
		List<ShowDTO> showDTOs = new ArrayList<>();
        for (Show show : showRepository.findAll()) {
            if (show.getShowDate() != null && show.getShowDate().isEqual(date)) {
                showDTOs.add(modelMapper.map(show, ShowDTO.class));
            }
        }
        return showDTOs;
	}

}
