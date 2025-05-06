package com.gn.booking.api.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter	
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
	
    private int roomId;
    private String roomName;
    private String title;
    private Instant startTime;
    private Instant endTime;
    
}
