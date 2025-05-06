package com.gn.booking.infra.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import com.gn.booking.domain.document.BookingDocument;

@EnableElasticsearchRepositories
public interface BookingElasticRepository 
	extends ElasticsearchRepository<BookingDocument, UUID>{
	
	List<BookingDocument> findByTitle(String title);
}
