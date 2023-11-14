package com.cg.mts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.dto.ScreenDTO;
import com.cg.mts.dto.TheatreDTO;
import com.cg.mts.exception.ScreenNotFoundException;
import com.cg.mts.service.ScreenService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/screens")
public class ScreenController {

	Logger logger = LoggerFactory.getLogger(ScreenController.class);

	@Autowired
	private ScreenService screenService;

	
	@PostMapping("/add")
	public ResponseEntity<ScreenDTO> addAScreen(@RequestBody ScreenDTO screenDTO,
			@RequestParam(required = false) Integer theatreId)
			throws ScreenNotFoundException {
		
		 ScreenDTO addedScreenDTO = screenService.addScreen(screenDTO, theatreId);
		 logger.info("-------Screen Successfully added into Theatre " + theatreId + "---------");
	        return ResponseEntity.ok(addedScreenDTO);
	}


	@GetMapping("/findall")
	public ResponseEntity<List<ScreenDTO>> viewScreenList() throws  ScreenNotFoundException {

		logger.info("-------List Of Screens Fetched Successfully---------");
		return ResponseEntity.ok(screenService.viewScreenList());
	}
	
	@GetMapping("/theatre/{screenId}")
	public ResponseEntity<TheatreDTO>  getTheatreById(@PathVariable int screenId) throws ScreenNotFoundException {
		TheatreDTO theatreDTO = screenService.getTheatre(screenId);
        return new ResponseEntity<>(theatreDTO, HttpStatus.OK);
	}
	
	@GetMapping("/viewScreen/{screenId}")
	public ResponseEntity<ScreenDTO> viewScreen(@PathVariable int screenId)
			throws ScreenNotFoundException {
		 ScreenDTO screenDTO = screenService.viewScreen(screenId);
		    return ResponseEntity.ok(screenDTO);
	}

	
	@PutMapping("/update")
	public ResponseEntity<ScreenDTO> updateScreen(@RequestBody ScreenDTO screenDTO, @RequestParam(required = false) Integer theatreId)
			throws  ScreenNotFoundException {

		 ScreenDTO updatedScreen = screenService.updateScreen(screenDTO, theatreId);
	        return new ResponseEntity<>(updatedScreen, HttpStatus.OK);
	}
}
