package com.cg.mts.service;

import java.util.List;

import com.cg.mts.dto.TicketDTO;
import com.cg.mts.entity.Ticket;
import com.cg.mts.exception.TicketNotFoundException;

public interface TicketService {
	
	public TicketDTO addTicket(TicketDTO ticketDTO,Integer bookingId) throws TicketNotFoundException;

	public TicketDTO findTicket(int ticketId) throws TicketNotFoundException;

	List<TicketDTO> viewTicketList() throws TicketNotFoundException;

}
