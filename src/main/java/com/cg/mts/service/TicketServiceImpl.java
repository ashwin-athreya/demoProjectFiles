package com.cg.mts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.mts.dto.BookingDTO;
import com.cg.mts.dto.TicketDTO;
import com.cg.mts.entity.Booking;
import com.cg.mts.entity.Ticket;
import com.cg.mts.exception.TicketNotFoundException;
import com.cg.mts.repository.BookingRepository;
import com.cg.mts.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

	private TicketRepository ticketRepository;

	public TicketServiceImpl(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Override
	public TicketDTO addTicket(TicketDTO ticketDTO,Integer bookingId) throws TicketNotFoundException {
		Booking booking = new Booking();
        if (bookingId != null) {
            booking = bookingRepository.findById(bookingId).orElseThrow(() -> new TicketNotFoundException("Booking not found"));
            booking.setTransactionStatus("Completed");
            ticketDTO.setBooking(modelMapper.map(booking, BookingDTO.class));
        }

        Ticket ticketEntity = modelMapper.map(ticketDTO, Ticket.class);

        ticketRepository.saveAndFlush(ticketEntity);

        return modelMapper.map(ticketEntity, TicketDTO.class);
	}

	@Override
	public List<TicketDTO> viewTicketList() throws TicketNotFoundException {
		List<Ticket> tickets = ticketRepository.findAll();
		if (tickets.isEmpty()) {
			throw new TicketNotFoundException("No tickets are available");
		}
		return tickets.stream()
				.map(ticket -> modelMapper.map(ticket, TicketDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public TicketDTO findTicket(int ticketId) throws TicketNotFoundException {
		Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + ticketId));

        return modelMapper.map(ticket, TicketDTO.class);
	}

}
