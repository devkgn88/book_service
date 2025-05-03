package com.gn.book.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import com.gn.book.document.BookingDocument;

@EnableElasticsearchRepositories
public interface BookingSearchRepository 
	extends ElasticsearchRepository<BookingDocument, UUID>{
	
	List<BookingDocument> findByTitle(String title);
}
