package com.appointments.api.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "appointments")
public class AppointmentModel {

    @Id
    private String id;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime from;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime to;

    private String subject;

    private String description;

    public AppointmentModel() {
    }

    public AppointmentModel(LocalDateTime from, LocalDateTime to, String subject, String description) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.description = description;
    }

    public AppointmentModel(String id, LocalDateTime from, LocalDateTime to, String subject, String description) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
