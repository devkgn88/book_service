package com.gn.booking.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gn.booking.api.dto.BookingRequest;
import com.gn.booking.infra.kafka.BookingProducer;
import com.gn.booking.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BookingController {

	private final BookingService bookingService;
    private final BookingProducer bookingProducer;
	
    @PostMapping("/api/bookings")
    public ResponseEntity<String> createBooking(@RequestBody BookingRequest request) {
        try {
            boolean conflict = bookingService.isDuplicateBooking(request);
            if (conflict) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 예약이 존재합니다.");
            }

            bookingProducer.sendBooking(request); // Kafka로 전송
            return ResponseEntity.ok("예약 요청이 처리되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("중복 확인 중 오류 발생");
        }
    }
    
//    @PostMapping("/api/test/save")
//    public BookingDocument saveBooking() {
//    	BookingDocument booking = new BookingDocument();
//    	booking.setId(UUID.randomUUID());
//    	booking.setRoom_id(102);
//    	booking.setRoom_name("회의실 B");
//    	booking.setTitle("주간 회의");
//    	booking.setStart_time(Instant.now());
//    	booking.setEnd_time(booking.getStart_time().plusSeconds(3600));
//    	
//        return bookingSearchRepository.save(booking);
//    }
//    
//    @GetMapping("/api/test/search")
//    public List<BookingDocument> searchByTitle(@RequestParam("title") String title) {
//        return bookingSearchRepository.findByTitle(title);
//    }
//    
//    @PostMapping("/api/test/check-duplication")
//    public ResponseEntity<?> checkDuplicate(@RequestBody BookingDocument bookingRequest) {
//        boolean isDuplicate = bookingService.isDuplicateBooking(
//            bookingRequest.getRoom_id(),
//            bookingRequest.getStart_time(),
//            bookingRequest.getEnd_time()
//        );
//        return ResponseEntity.ok(Map.of("duplicate", isDuplicate));
//    }

}
