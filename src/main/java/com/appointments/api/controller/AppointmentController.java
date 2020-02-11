package com.appointments.api.controller;

import com.appointments.api.model.AppointmentModel;
import com.appointments.api.service.AppointmentService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public List<AppointmentModel> getAppointments() {
        return appointmentService.findAll();
    }

    @PostMapping
    public AppointmentModel save(@Valid @RequestBody AppointmentModel appointmentModel) {
        return appointmentService.save(appointmentModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String pathId) {
        Optional<AppointmentModel> exist = appointmentService.findById(pathId);
        if (exist.isPresent()) {
            appointmentService.delete(pathId);
            return new ResponseEntity(HttpStatus.OK);
        } else
            return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentModel> getById(@PathVariable("id") String pathId) {
        Optional<AppointmentModel> exist = appointmentService.findById(pathId);
        if (exist.isPresent()) {
            return new ResponseEntity(exist, HttpStatus.OK);
        } else
            return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

}
