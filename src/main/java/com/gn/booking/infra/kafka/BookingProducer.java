package com.gn.booking.infra.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.gn.booking.api.dto.BookingRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingProducer {

	private final KafkaTemplate<String, BookingRequest> kafkaTemplate;

    public void sendBooking(BookingRequest request) {
        kafkaTemplate.send("booking-topic", request);
    }
}
