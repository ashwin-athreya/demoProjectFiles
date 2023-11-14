package com.cg.mts.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.mts.dto.TheatreDTO;
import com.cg.mts.entity.Movie;
import com.cg.mts.entity.Show;
import com.cg.mts.entity.Theatre;
import com.cg.mts.exception.TheatreNotFoundException;
import com.cg.mts.repository.MoviesRepository;
import com.cg.mts.repository.ScreenRepository;
import com.cg.mts.repository.TheatreRepository;

@Service
public class TheatreServiceImpl implements TheatreService {

	@Autowired
	private TheatreRepository theatrerepository;
	@Autowired
	ScreenRepository screenRepository;
	@Autowired
	private MoviesRepository moviesrepository;
	@Autowired
    private ModelMapper modelMapper;

	@Override
	public List<TheatreDTO> getAllTheatres() throws TheatreNotFoundException {
		 List<Theatre> theatres = theatrerepository.findAll();
	        List<TheatreDTO> theatreDTO = new ArrayList<>();

	        for (Theatre theatre : theatres) {
	            theatreDTO.add(modelMapper.map(theatre, TheatreDTO.class));
	        }

	        return theatreDTO;
	}

	@Override
	public TheatreDTO findTheatres(int theatreId) {
		 if (theatrerepository.findById(theatreId).isPresent()) {
	            Theatre theatre = theatrerepository.findById(theatreId).get();
	            return modelMapper.map(theatre, TheatreDTO.class);
	        } else {
	            return null;
	        }
	}

	@Override
	public TheatreDTO addTheatre(TheatreDTO theatreDTO) throws TheatreNotFoundException {
		if (theatreDTO != null) {
            Theatre theatre = modelMapper.map(theatreDTO, Theatre.class);
            if (theatrerepository.existsById(theatre.getTheatreId())) {
                throw new TheatreNotFoundException("Theatre already exists");
            } else {
                theatrerepository.saveAndFlush(theatre);
            }
        }
        return theatreDTO;
	}

	@Override
	public List<TheatreDTO> updateTheatre(TheatreDTO theatreDTO) throws TheatreNotFoundException {
		Theatre theatre = modelMapper.map(theatreDTO, Theatre.class);
        theatrerepository.save(theatre);
        return getAllTheatres();
	}

	@Override
	public List<TheatreDTO> deleteTheatreById(int theatreId) throws TheatreNotFoundException{
		theatrerepository.deleteById(theatreId);
        return getAllTheatres();
	}

	@Override
	public List<TheatreDTO> findTheatresByMovie(Integer movieId) throws TheatreNotFoundException {
		List<TheatreDTO> theatreDTOList = new ArrayList<>();
        Movie movie = moviesrepository.findById(movieId).orElse(null);

        if (movie != null) {
            Integer showId = movie.getShow().getShowId();
            List<Theatre> theatres = theatrerepository.findAll();

            for (Theatre theatre : theatres) {
                List<Show> shows = theatre.getShow();
                for (Show show : shows) {
                    if (show.getShowId() == showId) {
                        theatreDTOList.add(modelMapper.map(theatre, TheatreDTO.class));
                    }
                }
            }
        }
        return theatreDTOList;
	}

}
