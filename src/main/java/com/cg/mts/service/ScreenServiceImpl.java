package com.cg.mts.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.mts.dto.ScreenDTO;
import com.cg.mts.dto.TheatreDTO;
import com.cg.mts.entity.Screen;
import com.cg.mts.entity.Theatre;
import com.cg.mts.exception.ScreenNotFoundException;
import com.cg.mts.repository.ScreenRepository;
import com.cg.mts.repository.TheatreRepository;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;


@Service
public class ScreenServiceImpl implements ScreenService {

	@Autowired
	private ScreenRepository screenRepository;
	@Autowired
	private TheatreRepository theatreRepository;
	
	@Autowired
	private ModelMapper modelMapper;


	@Override
	public List<ScreenDTO> viewScreenList() throws ScreenNotFoundException {
		List<Screen> screens = screenRepository.findAll();
        if (screens.isEmpty()) {
            throw new ScreenNotFoundException("No screens found");
        }

        return screens.stream()
                .map(screen -> modelMapper.map(screen, ScreenDTO.class))
                .collect(Collectors.toList());
	}

	
	@Override
	public ScreenDTO addScreen(ScreenDTO screenDTO, Integer theatreId) throws ScreenNotFoundException {
		Screen screen = modelMapper.map(screenDTO, Screen.class);
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new ScreenNotFoundException("Theatre not found"));
        screen.setTheatre(theatre);
        screen = screenRepository.saveAndFlush(screen);
        return modelMapper.map(screen, ScreenDTO.class);

	}
	@Override
	public ScreenDTO viewScreen(int screenId) throws ScreenNotFoundException {
		Screen screen = screenRepository.findById(screenId)
	            .orElseThrow(() -> new ScreenNotFoundException("Screen not found"));
	    return modelMapper.map(screen, ScreenDTO.class);

		}

	@Override
	public ScreenDTO updateScreen(ScreenDTO screenDTO, Integer theatreId) throws ScreenNotFoundException {
		Screen existingScreen = screenRepository.findById(screenDTO.getScreenId())
                .orElseThrow(() -> new ScreenNotFoundException("Screen not found"));

        Theatre theatre = null;
        if (theatreId != null) {
            theatre = theatreRepository.findById(theatreId)
                    .orElseThrow(() -> new ScreenNotFoundException("Theatre not found"));
            existingScreen.setTheatre(theatre);
        }

        // Update other fields if needed
        existingScreen.setScreenName(screenDTO.getScreenName());
        existingScreen.setRows(screenDTO.getRows());
        existingScreen.setColumns(screenDTO.getColumns());

        screenRepository.saveAndFlush(existingScreen);

        return modelMapper.map(existingScreen, ScreenDTO.class);
	}

	@Override
	public TheatreDTO getTheatre(int screenId) throws ScreenNotFoundException {
		 Screen screen = screenRepository.findById(screenId)
	                .orElseThrow(() -> new ScreenNotFoundException("Screen not found"));

	        Theatre theatre = screen.getTheatre();
	        return modelMapper.map(theatre, TheatreDTO.class);
	}

}
