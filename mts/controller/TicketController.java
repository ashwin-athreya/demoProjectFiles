package com.cg.mts.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.dto.TicketDTO;
import com.cg.mts.entity.Ticket;
import com.cg.mts.exception.AccessForbiddenException;
import com.cg.mts.exception.TicketNotFoundException;
import com.cg.mts.service.TicketService;

/**
 * 
 * @author Yogendra
 *
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/tickets")
public class TicketController {

	Logger logger = LoggerFactory.getLogger(TicketController.class);

	@Autowired
	private TicketService ticketService;
	@Autowired
	LoginController loginController;
	
	@Autowired
    private ModelMapper modelMapper;

	/**
	 * 
	 * @param ticket
	 * @return added ticket
	 * @throws AccessForbiddenException
	 * @throws TicketNotFoundException
	 */
	@PostMapping("/add")
	public ResponseEntity<TicketDTO> addATicket(@RequestBody TicketDTO ticketDTO,@RequestParam(required = false) Integer bookingId)
			throws AccessForbiddenException, TicketNotFoundException {
		
		TicketDTO resultDTO = ticketService.addTicket(ticketDTO, bookingId);
		/* System.out.println(ticket.getSeats()); */
		logger.info("-------Ticked Created Successfully---------");
		return new ResponseEntity<>(resultDTO, HttpStatus.CREATED);
	}

	/**
	 * 
	 * @return ticketList
	 * @throws AccessForbiddenException
	 * @throws TicketNotFoundException
	 */
	@GetMapping("/findall")
	public ResponseEntity<List<TicketDTO>> viewTicketList() throws AccessForbiddenException, TicketNotFoundException {
		
		List<TicketDTO> ticketDTOs = ticketService.viewTicketList();
		logger.info("-------List of Tickets Found Successfully---------");
		return ResponseEntity.ok(ticketDTOs);
	}

	/**
	 * 
	 * @param ticketId
	 * @return ticket by ticketId
	 * @throws TicketNotFoundException
	 * @throws AccessForbiddenException
	 */
	@GetMapping("/{ticketId}")
	public ResponseEntity<TicketDTO> findATicket(@PathVariable int ticketId) throws TicketNotFoundException, AccessForbiddenException {	
		TicketDTO resultDTO;
        try {
            resultDTO = ticketService.findTicket(ticketId);
            logger.info("-------Ticket with ticketId " + ticketId + " Found Successfully---------");
        } catch (Exception e) {
            throw new TicketNotFoundException("Invalid Ticket ID");
        }
        return ResponseEntity.ok(resultDTO);

	}

}
