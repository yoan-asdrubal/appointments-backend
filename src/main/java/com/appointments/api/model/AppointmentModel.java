package com.appointments.api.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class Appointment {

    @Id
    private ObjectId id;

    private Date from;

    private Date to;

    private String subject;

    private String description;
}
