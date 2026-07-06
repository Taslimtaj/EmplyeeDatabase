package com.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createEmployeeReturns201WhenFieldsArePresent() throws Exception {
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "employeeId", "E001",
                                "employeeName", "Jane Doe"
                        ))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId").value("E001"))
                .andExpect(jsonPath("$.employeeName").value("Jane Doe"));
    }

    @Test
    void createEmployeeReturns400WhenMandatoryFieldsAreMissing() throws Exception {
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "employeeId", "E002"
                        ))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllEmployeesReturnsList() throws Exception {
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "employeeId", "E010",
                                "employeeName", "John Smith"
                        ))));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].employeeId").value("E010"));
    }

    @Test
    void updateEmployeeReturnsUpdatedRecord() throws Exception {
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "employeeId", "E020",
                                "employeeName", "Alice"
                        ))));

        mockMvc.perform(put("/api/employees/E020")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "employeeId", "E020",
                                "employeeName", "Alice Updated"
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeName").value("Alice Updated"));
    }

    @Test
    void deleteEmployeeReturns204() throws Exception {
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "employeeId", "E030",
                                "employeeName", "Bob"
                        ))));

        mockMvc.perform(delete("/api/employees/E030"))
                .andExpect(status().isNoContent());
    }
}
