package com.gn.booking.infra.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.gn.booking.domain.entity.Booking;

@EnableCassandraRepositories
public interface BookingCassandraRepository 
	extends CassandraRepository<Booking, UUID>{

}
