package com.gn.book.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.gn.book.domain.Booking;

@EnableCassandraRepositories
public interface BookingRepository 
	extends CassandraRepository<Booking, UUID>{

}
