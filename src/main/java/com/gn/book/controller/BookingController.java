package com.gn.book.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gn.book.domain.Booking;
import com.gn.book.request.BookingRequest;
import com.gn.book.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BookingController {

	private final BookingService bookingService;
	
    @PostMapping("/api/bookings")
    public Booking createBooking(@RequestBody BookingRequest bookingRequest) {
        return bookingService.saveBooking(bookingRequest);
    }
}
