package com.example.demo;

import com.example.demo.entities.AppUser;
import com.example.demo.entities.Department;
import com.example.demo.models.SaveAndUpdateEmployeeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartmentTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    // Test Add Department
    @Test
    public void testAddDepartment() throws Exception {
        // add employee as admin for testing
        mockMvc.perform(MockMvcRequestBuilders
                .post("/admin")
                .contentType(MediaType.APPLICATION_JSON));

        // generate token.
        AppUser user = new AppUser("areej", "obaid", null);
        String jsonRequest = mapper.writeValueAsString(user);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/login")
                .accept(MediaType.APPLICATION_JSON).content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        String token = "Bearer " + result.getResponse().getContentAsString();

        // Add Department
        Department department = new Department("any thing" + ((int)Math.random()*9000));
        String jsonRequestDepartment = mapper.writeValueAsString(department);

        RequestBuilder addDepartment = MockMvcRequestBuilders
                .post("/department")
                .accept(MediaType.APPLICATION_JSON).content(jsonRequestDepartment)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token);

        MvcResult addResult = mockMvc.perform(addDepartment).andReturn();
        assertEquals(200, addResult.getResponse().getStatus());
    }
}
