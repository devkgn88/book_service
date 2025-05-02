package com.gn.book.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.gn.book.domain.Booking;
import com.gn.book.repository.BookingRepository;
import com.gn.book.request.BookingRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {
	
	private final BookingRepository bookingRepository;
	
	public Booking saveBooking(BookingRequest bookingRequest) {
		Booking booking = Booking.builder()
				.id(UUID.randomUUID())
				.room_id(bookingRequest.getRoomId())
				.room_name(bookingRequest.getRoomName())
				.title(bookingRequest.getTitle())
				.start_time(bookingRequest.getStartTime())
				.end_time(bookingRequest.getEndTime())
				.build();

        return bookingRepository.save(booking);
	}

}
