package com.appointments.api.service;

import com.appointments.api.IApiApplicationTest;
import com.appointments.api.model.AppointmentModel;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AppointmentServiceTest implements IApiApplicationTest {
    @Autowired
    private AppointmentService appointmentService;

    @Test
    void getAllAppointments() {
        List<AppointmentModel> appointmentModels = new ArrayList<>();
        appointmentModels.add(new AppointmentModel(
                LocalDateTime.of(2020, Month.FEBRUARY, 01, 0, 0),
                "8:00 AM", "12:00 PM",
                "Metting", "Description for Meeting test", "RH"
        ));
        appointmentModels.add(new AppointmentModel(
                LocalDateTime.of(2020, Month.FEBRUARY, 01, 0, 0),
                "11:00 AM", "2:00 PM",
                "Appointment", "Description for Apointment test", "GER"
        ));

        appointmentService.saveAll(appointmentModels);

        List<AppointmentModel> appointmentModelList = appointmentService.findAll();

        Assertions.assertEquals(appointmentModelList.size(), 2);

        int size = appointmentModelList.size();
        for (int i = 0; i < size; i++) {
            AppointmentModel apModel = appointmentModels.get(i);
            AppointmentModel apResult = appointmentModelList.get(i);
            Assertions.assertNotNull(apResult.getId());
            Assertions.assertEquals(apModel.getDate(), apResult.getDate());
            Assertions.assertEquals(apModel.getTimeInit(), apResult.getTimeInit());
            Assertions.assertEquals(apModel.getTimeEnd(), apResult.getTimeEnd());
            Assertions.assertEquals(apModel.getSubject(), apResult.getSubject());
            Assertions.assertEquals(apModel.getDescription(), apResult.getDescription());
            Assertions.assertEquals(apModel.getArea(), apResult.getArea());
        }

    }

    @Test
    void saveAppointmentAndFindById() {
        AppointmentModel appointmentModel = new AppointmentModel(
                LocalDateTime.of(2020, Month.FEBRUARY, 01, 0, 0),
                "8:00 AM", "12:00 PM",
                "Metting", "Description for Meeting test", "RH"
        );
        AppointmentModel saved = appointmentService.save(appointmentModel);
        Optional<AppointmentModel> appointmentModelById = appointmentService.findById(saved.getId());
        AppointmentModel result = appointmentModelById.orElse(null);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
    }

    @Test
    void findAppointmentAndDeleteById() {
        AppointmentModel appointmentModel = new AppointmentModel(
                LocalDateTime.of(2020, Month.FEBRUARY, 01, 0, 0),
                "8:00 AM", "12:00 PM",
                "Metting", "Description for Meeting test", "RH"
        );
        AppointmentModel saved = appointmentService.save(appointmentModel);
        Optional<AppointmentModel> appointmentModelById = appointmentService.findById(saved.getId());
        AppointmentModel result = appointmentModelById.orElse(null);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
        appointmentService.delete(saved.getId());
        saved = appointmentService.findById(saved.getId()).orElse(null);
        Assertions.assertNull(saved);
    }

    @BeforeEach
    void clearBeforeEach() {
        appointmentService.deleteAll();
    }

    @AfterEach
    void clearAfterEach() {
        appointmentService.deleteAll();
    }
}
