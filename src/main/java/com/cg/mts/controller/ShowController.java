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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.dto.ShowDTO;
import com.cg.mts.exception.ShowNotFoundException;
import com.cg.mts.service.ShowService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/shows")
public class ShowController {

	Logger logger = LoggerFactory.getLogger(ShowController.class);
	
	@Autowired
	private ShowService showService;

	
	@PostMapping("/add")
	public ResponseEntity<ShowDTO> addShow(@RequestBody ShowDTO showDTO, @RequestParam(required = false) Integer theatreId,
			@RequestParam(required = false) Integer screenId)  {

        ShowDTO addedShow = showService.addShow(showDTO, theatreId, screenId);
        logger.info("-------Show Added Successfully--------");
        return new ResponseEntity<>(addedShow, HttpStatus.CREATED);

	}

	
	@DeleteMapping("/delete/{showId}")
	public ResponseEntity<ShowDTO> removeShow(@PathVariable int showId)  {

		ShowDTO removedShow = showService.removeShow(showId);
        if (removedShow != null) {
            logger.info("-------Show with ShowId " + showId + " Deleted Successfully---------");
            return new ResponseEntity<>(removedShow, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}

	
	@PutMapping("/update")
	public ResponseEntity<ShowDTO> updateShow(@RequestBody ShowDTO showDTO, @RequestParam(required = false) Integer theatreId,
			@RequestParam(required = false) Integer screenId)  throws ShowNotFoundException{

		ShowDTO updatedShow = showService.updateShow(showDTO, theatreId, screenId);

        if (updatedShow != null) {
            return new ResponseEntity<>(updatedShow, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}

	
	@GetMapping("/view/{showId}")
	public ResponseEntity<ShowDTO> viewShow(@PathVariable int showId)
			throws  ShowNotFoundException {

		ShowDTO showDTO = showService.viewShow(showId);
        if (showDTO != null) {
            logger.info("-------Show with ShowId " + showId + " Found Successfully---------");
            return new ResponseEntity<>(showDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}

	
	@GetMapping("/findall")
	public ResponseEntity<List<ShowDTO>> viewShowList() {

		 List<ShowDTO> showDTOs = showService.viewAllShows();
	     logger.info("-------List Of Shows Fetched Successfully---------");
	        return ResponseEntity.ok(showDTOs);
	}


	@GetMapping("/show_theatre/{theatreId}")
	public ResponseEntity<List<ShowDTO>> viewShowByTheatreId(@PathVariable int theatreId) {

		 List<ShowDTO> showDTOs = showService.viewShowList(theatreId);
	        logger.info("-------List Of Shows With TheatreId " + theatreId + " Fetched Successfully---------");
	        return ResponseEntity.ok(showDTOs);
	}


	@GetMapping("/date/{date}")
	public ResponseEntity<List<ShowDTO>> viewShowByLocalDate(@PathVariable int date) {

		List<ShowDTO> showDTOs = showService.viewShowList(date);
        logger.info("-------List Of Shows With Date " + date + " Fetched Successfully---------");
        return ResponseEntity.ok(showDTOs);
	}
}
