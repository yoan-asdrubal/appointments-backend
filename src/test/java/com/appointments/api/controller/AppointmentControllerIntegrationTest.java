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
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@WebMvcTest
public class AppointmentControllerIntegrationTest implements IApiApplicationTest {

    public static final String baseUrl = "/api/appointment";
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllAppointments() throws Exception {
        List<AppointmentModel> appointmentModels = new ArrayList<>();
        appointmentModels.add(new AppointmentModel(
                Instant.now().toEpochMilli(),
                "8:00 AM", "12:00 PM",
                "Metting", "Description for Meeting test", "RH"
        ));
        appointmentModels.add(new AppointmentModel(
                Instant.now().toEpochMilli(),
                "11:00 AM", "2:00 PM",
                "Appointment", "Description for Apointment test", "GER"
        ));
        Mockito.when(appointmentService.findAll()).thenReturn(appointmentModels);

        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2))).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void saveAppointment() throws Exception {
        Long date = Instant.now().toEpochMilli();
        AppointmentModel appointmentModel = new AppointmentModel(
                new ObjectId().toString(),
                date,
                "8:00 AM", "12:00 PM",
                "Metting", "Description for Meeting test", "RH"
        );
        String id = new ObjectId().toString();

        Map params = new HashMap();
        params.put("id", id);
        params.put("date", date);
        params.put("timeInit", "08:00 AM");
        params.put("timeEnd", "12:00 PM");
        params.put("subject", "Metting");
        params.put("description", "Description for Meeting test");
        params.put("area", "RH");

        Mockito.when(appointmentService.save(ArgumentMatchers.any(AppointmentModel.class))).thenReturn(appointmentModel);

        String stringParams = objectMapper.writeValueAsString(params);
        System.out.println(stringParams);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                .content(stringParams)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        AppointmentModel result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AppointmentModel.class);
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(appointmentModel.getDate(), result.getDate());
        Assertions.assertEquals(appointmentModel.getSubject(), result.getSubject());
        Assertions.assertEquals(appointmentModel.getDescription(), result.getDescription());

    }

    @Test
    void deleteAppointmentSuccess() throws Exception {
        AppointmentModel appointmentModel = new AppointmentModel(
                new ObjectId().toString(),
                Instant.now().toEpochMilli(),
                "8:00 AM", "12:00 PM",
                "Metting", "Description for Meeting test", "RH"
        );

        Mockito.doNothing().when(appointmentService).delete(ArgumentMatchers.any(String.class));

        Mockito.when(appointmentService.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(appointmentModel));

        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/{id}", appointmentModel.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void deleteAppointmentNotFoundError() throws Exception {

        Mockito.doNothing().when(appointmentService).delete(ArgumentMatchers.any(String.class));
        Mockito.when(appointmentService.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/{id}", new ObjectId().toString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void getAppointmentById() throws Exception {
        AppointmentModel appointmentModel = new AppointmentModel(
                new ObjectId().toString(),
                Instant.now().toEpochMilli(),
                "8:00 AM", "12:00 PM",
                "Metting", "Description for Meeting test", "RH"
        );


        Mockito.when(appointmentService.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(appointmentModel));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{id}", appointmentModel.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        AppointmentModel result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AppointmentModel.class);
        Assertions.assertNotNull(result.getId());
        Assertions.assertNotEquals(result.getId(), "");
        Assertions.assertEquals(appointmentModel.getDate(), result.getDate());
        Assertions.assertEquals(appointmentModel.getSubject(), result.getSubject());
        Assertions.assertEquals(appointmentModel.getDescription(), result.getDescription());
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
