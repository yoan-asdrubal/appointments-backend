package com.appointments.api.controller;

import com.appointments.api.model.AppointmentModel;
import com.appointments.api.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public List<AppointmentModel> getAppointments() {
        return appointmentService.findAll();
    }
}
