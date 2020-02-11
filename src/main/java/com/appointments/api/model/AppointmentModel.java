package com.appointments.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Document(collection = "appointments")
public class AppointmentModel {

    @Id
    private String id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;


    private String timeInit;

    private String timeEnd;

    private String subject;

    private String description;

    private String area;

    public AppointmentModel() {
    }

    public AppointmentModel(String id, LocalDateTime date, String timeInit, String timeEnd, String subject, String description, String area) {
        this.id = id;
        this.date = date;
        this.timeInit = timeInit;
        this.timeEnd = timeEnd;
        this.subject = subject;
        this.description = description;
        this.area = area;
    }

    public AppointmentModel(LocalDateTime date, String timeInit, String timeEnd, String subject, String description, String area) {
        this.id = null;
        this.date = date;
        this.timeInit = timeInit;
        this.timeEnd = timeEnd;
        this.subject = subject;
        this.description = description;
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public String getTimeInit() {
        return timeInit;
    }

    public void setTimeInit(String timeInit) {
        this.timeInit = timeInit;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
