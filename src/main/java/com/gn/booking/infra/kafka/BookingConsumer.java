package com.gn.booking.infra.kafka;


import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.gn.booking.api.dto.BookingRequest;
import com.gn.booking.domain.document.BookingDocument;
import com.gn.booking.domain.entity.Booking;
import com.gn.booking.infra.repository.BookingCassandraRepository;
import com.gn.booking.infra.repository.BookingElasticRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookingConsumer {
	
	private final BookingCassandraRepository bookingCassandraRepository;
	private final BookingElasticRepository bookingElasticRepository;
	
	@KafkaListener(topics="booking-topic", groupId="booking-group")
	public void consume(BookingRequest bookingRequest){
		// 1. UUID 생성
        UUID uuid = UUID.randomUUID();
		
		// 2. Dto를 Entity로 변환
		Booking booking = Booking.builder()
				.id(uuid)
				.room_id(bookingRequest.getRoomId())
				.room_name(bookingRequest.getRoomName())
				.title(bookingRequest.getTitle())
				.start_time(bookingRequest.getStartTime())
				.end_time(bookingRequest.getEndTime())
				.build();
		
		// 3. Cassandra에 저장
		bookingCassandraRepository.save(booking);
		
		// 4. Dto를 Document로 변환
		BookingDocument doc = BookingDocument.builder()
                .id(uuid)
                .room_id(bookingRequest.getRoomId())
                .room_name(bookingRequest.getRoomName())
                .title(bookingRequest.getTitle())
                .start_time(bookingRequest.getStartTime())
                .end_time(bookingRequest.getEndTime())
                .build();
		
		// 5. ElasticSearch 저장
        bookingElasticRepository.save(doc);
	}
}

