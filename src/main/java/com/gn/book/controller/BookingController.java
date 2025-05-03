package com.gn.book.controller;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gn.book.document.BookingDocument;
import com.gn.book.domain.Booking;
import com.gn.book.repository.BookingSearchRepository;
import com.gn.book.request.BookingRequest;
import com.gn.book.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BookingController {

	private final BookingService bookingService;
    private final BookingSearchRepository bookingSearchRepository;
	
    @PostMapping("/api/bookings")
    public Booking createBooking(@RequestBody BookingRequest bookingRequest) {
        return bookingService.saveBooking(bookingRequest);
    }
    
    @PostMapping("/api/test/save")
    public BookingDocument saveBooking() {
    	BookingDocument booking = new BookingDocument();
    	booking.setId(UUID.randomUUID());
    	booking.setRoom_id(101);
    	booking.setRoom_name("회의실 A");
    	booking.setTitle("멘토링 회의");
    	booking.setStart_time(Instant.now());
    	booking.setEnd_time(booking.getStart_time().plusSeconds(3600));
    	
        return bookingSearchRepository.save(booking);
    }
    
    @GetMapping("/api/test/search")
    public List<BookingDocument> searchByTitle(@RequestParam("title") String title) {
        return bookingSearchRepository.findByTitle(title);
    }
    
    
    
    
}
