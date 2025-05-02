package com.gn.book.request;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {
	
    private int roomId;
    private String roomName;
    private String title;
    private Instant startTime;
    private Instant endTime;
    
}
