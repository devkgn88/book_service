package com.gn.booking.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import com.gn.booking.api.dto.BookingRequest;
import com.gn.booking.domain.document.BookingDocument;
import com.gn.booking.domain.entity.Booking;
import com.gn.booking.infra.repository.BookingCassandraRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

	private final BookingCassandraRepository bookingCassandraRepository;
	private final ElasticsearchOperations elasticsearchOperations;
	
	public Booking saveBooking(BookingRequest bookingRequest) {
		Booking booking = Booking.builder()
				.id(UUID.randomUUID())
				.room_id(bookingRequest.getRoomId())
				.room_name(bookingRequest.getRoomName())
				.title(bookingRequest.getTitle())
				.start_time(bookingRequest.getStartTime())
				.end_time(bookingRequest.getEndTime())
				.build();

        return bookingCassandraRepository.save(booking);
	}
	
	public boolean isDuplicateBooking(BookingRequest request) {
		Criteria criteria = new Criteria("room_id").is(request.getRoomId())
		        .and(new Criteria("start_time").lessThan(request.getEndTime()))     // 기존 예약이 끝나기 전에 시작되면 겹침
		        .and(new Criteria("end_time").greaterThan(request.getStartTime())); // 기존 예약이 시작된 이후에 끝나면 겹침

	    Query query = new CriteriaQuery(criteria);

	    List<BookingDocument> results = elasticsearchOperations.search(query, BookingDocument.class)
	                                                            .stream()
	                                                            .map(SearchHit::getContent)
	                                                            .toList();

	    return !results.isEmpty(); // 결과가 하나라도 있으면 겹치는 예약 있음
	}
}
