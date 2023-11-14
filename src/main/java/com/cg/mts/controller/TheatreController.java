package com.cg.mts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.dto.TheatreDTO;
import com.cg.mts.exception.TheatreNotFoundException;
import com.cg.mts.service.TheatreService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/theatre")
public class TheatreController {
	Logger logger = LoggerFactory.getLogger(TheatreController.class);
	@Autowired
	private TheatreService theatreservice;


	
	@GetMapping("/all")
	public ResponseEntity<List<TheatreDTO>> getAlltheatres() throws  TheatreNotFoundException {

		 List<TheatreDTO> theatreDTOs = theatreservice.getAllTheatres();
		 logger.info("-------Theatre List Fetched---------");
	        return ResponseEntity.ok(theatreDTOs);
		
	}

	
	@PostMapping("/insert")
	public ResponseEntity<TheatreDTO> addTheatre(@RequestBody TheatreDTO theatreDTO)
			throws TheatreNotFoundException {

		logger.info("-------Theatre Added Successfully---------");
		return new ResponseEntity<>(theatreservice.addTheatre(theatreDTO), HttpStatus.CREATED);
	}


	@PutMapping("/update")
	public ResponseEntity<List<TheatreDTO>> updateTheatre(@RequestBody TheatreDTO theatreDTO)
			throws  TheatreNotFoundException {

		 List<TheatreDTO> updatedTheatres = theatreservice.updateTheatre(theatreDTO);
		 logger.info("-------Theatre Updated Successfully---------");
	        return ResponseEntity.ok(updatedTheatres);
		
		
	}


	@GetMapping("/find/{theatreId}")
	public ResponseEntity<TheatreDTO> findTheatre(@PathVariable int theatreId)
			throws  TheatreNotFoundException {

		TheatreDTO theatreDTO = theatreservice.findTheatres(theatreId);
		logger.info("-------Theatre Found with Theatre id" + theatreId + "---------");
        return ResponseEntity.ok(theatreDTO);
	}

	
	@DeleteMapping("/delete/{theatreId}")
	public ResponseEntity<List<TheatreDTO>> deleteMoviesById(@PathVariable int theatreId)
			throws TheatreNotFoundException {

		 List<TheatreDTO> updatedTheatres = theatreservice.deleteTheatreById(theatreId);
		 logger.info("-------Theatre Deleted with Theatre id" + theatreId + "---------");
	        return ResponseEntity.ok(updatedTheatres);
	}
	
	
	@GetMapping("/findbyMovie/{movieId}")
	public ResponseEntity<List<TheatreDTO>> findTheatreByMovieId(@PathVariable int movieId)
			throws  TheatreNotFoundException {
		List<TheatreDTO> theatreDTOList = theatreservice.findTheatresByMovie(movieId);
        return ResponseEntity.ok(theatreDTOList);
	}
	
}
