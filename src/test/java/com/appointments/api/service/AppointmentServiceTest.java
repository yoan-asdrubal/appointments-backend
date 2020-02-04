package com.appointments.api.service;

import com.appointments.api.model.AppointmentModel;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AppointmentServiceTest {
    @Autowired
    private AppointmentService appointmentService;

    @Test
    void getAllAppointments() {
        List<AppointmentModel> appointmentModels = new ArrayList<>();
        appointmentModels.add(new AppointmentModel(
                new ObjectId().toString(),
                LocalDateTime.of(2020, Month.FEBRUARY, 01, 8, 30),
                LocalDateTime.of(2020, 02, 01, 12, 30),
                "Metting", "Description for Meeting test"
        ));
        appointmentModels.add(new AppointmentModel(
                new ObjectId().toString(),
                LocalDateTime.of(2020, 02, 04, 10, 30),
                LocalDateTime.of(220, 02, 04, 17, 00),
                "Appointment", "Description for Appointment"
        ));

        appointmentService.saveAll(appointmentModels);

        List<AppointmentModel> appointmentModelList = appointmentService.findAll();

        Assertions.assertEquals(appointmentModelList.size(), 2);

        int size = appointmentModels.size();
        for (int i = 0; i < size; i++) {
            AppointmentModel apModel = appointmentModels.get(0);
            AppointmentModel apResult = appointmentModelList.get(0);
            Assertions.assertNotNull(apResult.getId());
            Assertions.assertEquals(apModel.getFrom(), apResult.getFrom());
            Assertions.assertEquals(apModel.getTo(), apResult.getTo());
            Assertions.assertEquals(apModel.getSubject(), apResult.getSubject());
            Assertions.assertEquals(apModel.getDescription(), apResult.getDescription());
        }

    }

    @BeforeEach
    void clearBeforeEach() {
        appointmentService.deleteAll();
    }

    @AfterEach
    void clearAfterAll() {
        appointmentService.deleteAll();
    }
}
