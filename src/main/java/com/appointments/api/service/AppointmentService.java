package com.appointments.api.service;

import com.appointments.api.model.AppointmentModel;
import com.appointments.api.repository.AppointmentsRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService implements IService<AppointmentModel, ObjectId> {

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    public AppointmentModel save(AppointmentModel model) {
        return appointmentsRepository.save(model);
    }


    @Override
    public void clearCollection() {
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
}
