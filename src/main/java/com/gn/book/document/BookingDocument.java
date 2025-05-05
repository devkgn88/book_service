package com.gn.book.document;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(indexName = "booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDocument {

    @Id
    private UUID id;

    private int room_id;
    private String room_name;
    private String title;
    
    @Field(type = FieldType.Date, format = DateFormat.epoch_millis)
    private Instant start_time;
    
    @Field(type = FieldType.Date, format = DateFormat.epoch_millis)
    private Instant end_time;
}
