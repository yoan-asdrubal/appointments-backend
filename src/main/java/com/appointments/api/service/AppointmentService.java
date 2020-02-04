package com.appointments.api.service;

import com.appointments.api.model.AppointmentModel;
import com.appointments.api.repository.AppointmentsRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService implements IService<AppointmentModel, ObjectId> {

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    public AppointmentModel save(AppointmentModel model) {
        return appointmentsRepository.save(model);
    }


    @Override
    public void deleteAll() {
        appointmentsRepository.deleteAll();
    }

    @Override
    public List<AppointmentModel> findAll() {
        return appointmentsRepository.findAll();
    }

    @Override
    public Iterable<AppointmentModel> saveAll(Iterable<AppointmentModel> models) {
        return appointmentsRepository.saveAll(models);
    }

    @Override
    public Optional<AppointmentModel> findById(ObjectId id) {
        return appointmentsRepository.findById(id);
    }
}
