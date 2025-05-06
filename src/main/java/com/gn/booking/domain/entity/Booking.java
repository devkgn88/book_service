package com.gn.booking.domain.entity;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table("booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

	@PrimaryKey
	private UUID id;
	
	private int room_id;
	private String room_name;
	private String title;
	private Instant start_time;
	private Instant end_time;
}
