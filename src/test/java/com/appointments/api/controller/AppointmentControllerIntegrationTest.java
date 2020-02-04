package com.appointments.api.controller;

import com.appointments.api.model.AppointmentModel;
import com.appointments.api.service.AppointmentService;
import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class AppointmentControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    @Test
    void getAllAppointments() throws Exception {
        List<AppointmentModel> aps = new ArrayList<>();
        aps.add(new AppointmentModel(
                new ObjectId().toString(),
                LocalDateTime.of(2020, Month.FEBRUARY, 01, 8, 30),
                LocalDateTime.of(2020, 02, 01, 12, 30),
                "Metting", "Description for Meeting test"
        ));
        aps.add(new AppointmentModel(
                new ObjectId().toString(),
                LocalDateTime.of(2020, 02, 04, 10, 30),
                LocalDateTime.of(220, 02, 04, 17, 00),
                "Appointment", "Description for Appointment"
        ));
        Mockito.when(appointmentService.findAll()).thenReturn(aps);

        mockMvc.perform(MockMvcRequestBuilders.get("/appointment")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2))).andDo(MockMvcResultHandlers.print());
    }

    @BeforeEach
    void clearCollection() {
        appointmentService.deleteAll();
    }
}
