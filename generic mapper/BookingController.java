package com.cg.mts.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cg.mts.dto.BookingDTO;
import com.cg.mts.entity.Booking;
import com.cg.mts.entity.Customer;
import com.cg.mts.entity.Show;
import com.cg.mts.exception.AccessForbiddenException;
import com.cg.mts.exception.BookingNotFoundException;
import com.cg.mts.mapper.EntityDtoMapper;
import com.cg.mts.service.IBookingService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/booking")
@Validated
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private IBookingService bookingService;

    @PostMapping(value = "/insert", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingDTO> addTicketBooking(@Valid @RequestBody BookingDTO bookingDTO,
            @RequestParam(required = false) Integer customerId, @RequestParam(required = false) Integer showId)
            throws AccessForbiddenException, BookingNotFoundException {
        logger.info("Adding a new booking");
        Booking booking = EntityDtoMapper.convertToEntity(bookingDTO, Booking.class);

        // Set Show in Booking
        if (showId != null) {
            Show show = new Show();
            show.setShowId(showId);
            booking.setShow(show);
        }

        Booking addedBooking = bookingService.addBooking(booking, customerId, showId);

        // Use the new method to handle the bidirectional relationship
        BookingDTO addedBookingDTO = EntityDtoMapper.convertToDTOWithRelationship(addedBooking, BookingDTO.class,
                b -> b.getShow().setBooking(null));
        
        logger.info("Booking added successfully");
        return ResponseEntity.ok(addedBookingDTO);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<BookingDTO>> viewAllBookings()
            throws AccessForbiddenException, BookingNotFoundException {
        logger.info("Fetching all bookings");
        List<Booking> bookingList = bookingService.viewBookingList();
        List<BookingDTO> bookingDTOList = EntityDtoMapper.convertToDTOList(bookingList, BookingDTO.class);
        logger.info("Bookings fetched successfully");
        return ResponseEntity.ok(bookingDTOList);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingDTO> updateTicketBooking(@Valid @RequestBody BookingDTO bookingDTO)
            throws BookingNotFoundException, AccessForbiddenException {
        logger.info("Updating booking");
        Booking booking = EntityDtoMapper.convertToEntity(bookingDTO, Booking.class);

        // Set Show in Booking
        if (bookingDTO.getShow() != null) {
            Show show = new Show();
            show.setShowId(bookingDTO.getShow().getShowId());
            booking.setShow(show);
        }

        Booking updatedBooking = bookingService.updateBooking(booking);
        BookingDTO updatedBookingDTO = EntityDtoMapper.convertToDTO(updatedBooking, BookingDTO.class);
        logger.info("Booking updated successfully");
        return ResponseEntity.ok(updatedBookingDTO);
    }



    @DeleteMapping("ticketbooking/{bookingId}")
    public ResponseEntity<BookingDTO> deleteTicketBookingById(@PathVariable int bookingId)
            throws BookingNotFoundException, AccessForbiddenException {
        logger.info("Deleting booking with ID: {}", bookingId);
        Booking deletedBooking = bookingService.cancelBooking(bookingId);
        BookingDTO deletedBookingDTO = EntityDtoMapper.convertToDTO(deletedBooking, BookingDTO.class);
        logger.info("Booking deleted successfully");
        return ResponseEntity.ok(deletedBookingDTO);
    }

    @GetMapping("/viewbooking/{bookingId}")
    public ResponseEntity<BookingDTO> viewBooking(@PathVariable int bookingId) throws BookingNotFoundException {
        logger.info("Fetching booking with ID: {}", bookingId);
        Booking booking = bookingService.viewBooking(bookingId);
        BookingDTO bookingDTO = EntityDtoMapper.convertToDTO(booking, BookingDTO.class);
        logger.info("Booking fetched successfully");
        return ResponseEntity.ok(bookingDTO);
    }

    @GetMapping("/byMovie/{movieId}")
    public ResponseEntity<List<BookingDTO>> viewMovieByMovieId(@PathVariable int movieId)
            throws AccessForbiddenException, BookingNotFoundException {
        logger.info("Fetching bookings for movie with ID: {}", movieId);
        List<Booking> bookings = bookingService.showAllBookings(movieId);
        List<BookingDTO> bookingDTOList = EntityDtoMapper.convertToDTOList(bookings, BookingDTO.class);
        logger.info("Bookings fetched successfully");
        return ResponseEntity.ok(bookingDTOList);
    }

    @GetMapping("/byDate/{date}")
    public ResponseEntity<List<BookingDTO>> viewMovieByLocalDate(
            @RequestParam("bookingDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
            throws AccessForbiddenException, BookingNotFoundException {
        logger.info("Fetching bookings for date: {}", date);
        List<Booking> bookings = bookingService.showAllBookings(date);
        List<BookingDTO> bookingDTOList = EntityDtoMapper.convertToDTOList(bookings, BookingDTO.class);
        logger.info("Bookings fetched successfully");
        return ResponseEntity.ok(bookingDTOList);
    }

    @GetMapping("/cost/{bookingId}")
    public ResponseEntity<Double> TotalBookingCost(@PathVariable int bookingId)
            throws AccessForbiddenException, BookingNotFoundException {
        logger.info("Calculating total cost for booking with ID: {}", bookingId);
        double totalCost = bookingService.calculateTotalCost(bookingId);
        logger.info("Total cost calculated successfully");
        return ResponseEntity.ok(totalCost);
    }
}
