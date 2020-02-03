package com.appointments.api.repository;

import com.appointments.api.model.AppointmentModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppointmentsRepository extends MongoRepository<AppointmentModel, ObjectId> {
}
