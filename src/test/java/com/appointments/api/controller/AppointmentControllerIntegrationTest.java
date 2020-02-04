package com.appointments.api.controller;

import com.appointments.api.IApiApplicationTest;
import com.appointments.api.model.AppointmentModel;
import com.appointments.api.service.AppointmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest
public class AppointmentControllerIntegrationTest implements IApiApplicationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    void saveAppointment() throws Exception {
        AppointmentModel appointmentModel = new AppointmentModel(
                new ObjectId().toString(),
                LocalDateTime.of(2020, 02, 04, 10, 30),
                LocalDateTime.of(220, 02, 04, 17, 00),
                "Appointment", "Description for Appointment"
        );
        String id = new ObjectId().toString();


        Mockito.when(appointmentService.save(ArgumentMatchers.any(AppointmentModel.class))).thenReturn(appointmentModel);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/appointment")
                .content(objectMapper.writeValueAsString(appointmentModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        AppointmentModel result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AppointmentModel.class);
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(appointmentModel.getFrom(), result.getFrom());
        Assertions.assertEquals(appointmentModel.getTo(), result.getTo());
        Assertions.assertEquals(appointmentModel.getSubject(), result.getSubject());
        Assertions.assertEquals(appointmentModel.getDescription(), result.getDescription());

    }

    @Test
    void deleteAppointmentSuccess() throws Exception {
        AppointmentModel appointmentModel = new AppointmentModel(
                new ObjectId().toString(),
                LocalDateTime.of(2020, 02, 04, 10, 30),
                LocalDateTime.of(220, 02, 04, 17, 00),
                "Appointment", "Description for Appointment"
        );

        Mockito.doNothing().when(appointmentService).delete(ArgumentMatchers.any(ObjectId.class));

        Mockito.when(appointmentService.findById(ArgumentMatchers.any(ObjectId.class))).thenReturn(Optional.of(appointmentModel));

        mockMvc.perform(MockMvcRequestBuilders.delete("/appointment/{id}", appointmentModel.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void deleteAppointmentNotFoundError() throws Exception {

        Mockito.doNothing().when(appointmentService).delete(ArgumentMatchers.any(ObjectId.class));
        Mockito.when(appointmentService.findById(ArgumentMatchers.any(ObjectId.class))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/appointment/{id}", new ObjectId().toString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @BeforeEach
    void clearBeforeEach() {
        appointmentService.deleteAll();
    }

    @BeforeEach
    void clearAfterEach() {
        appointmentService.deleteAll();
    }
}
