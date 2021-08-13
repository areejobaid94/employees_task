package com.example.demo;

import com.example.demo.entities.AppUser;
import com.example.demo.entities.Employee;
import com.example.demo.models.SaveAndUpdateEmployeeRequest;
import com.example.demo.models.SearchEmployeeRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private static final ObjectMapper objectMapper =
            new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // Test add employee
    @Test
    public void testAddEmployee() throws Exception {
        // add admin => just admin can add employee
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

        // add employee
        SaveAndUpdateEmployeeRequest employee = new SaveAndUpdateEmployeeRequest("first_name" , "last_name", 5000, true,1,null);
        String jsonRequestEmployee = mapper.writeValueAsString(employee);
        RequestBuilder addEmployee = MockMvcRequestBuilders
            .post("/employee")
            .accept(MediaType.APPLICATION_JSON).content(jsonRequestEmployee)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token);

        MvcResult addResult = mockMvc.perform(addEmployee).andReturn();
        assertEquals(200, addResult.getResponse().getStatus());
    }

    // Test delete Employee
    @Test
    public void testDeleteEmployee() throws Exception {
        // add admin => just admin can add employee
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

        // add Employee
        SaveAndUpdateEmployeeRequest employee = new SaveAndUpdateEmployeeRequest("first_name" , "last_name", 5000, true,1,null);
        String jsonRequestEmployee = mapper.writeValueAsString(employee);
        RequestBuilder addEmployee = MockMvcRequestBuilders
                .post("/employee")
                .accept(MediaType.APPLICATION_JSON).content(jsonRequestEmployee)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token);

        MvcResult addResult = mockMvc.perform(addEmployee).andReturn();
        AppUser appUser =  objectMapper.readValue(addResult.getResponse().getContentAsString(),AppUser.class);
        System.out.println(appUser.getEmployee().getId());

        // delete the new employee
        RequestBuilder deleteEmployee = MockMvcRequestBuilders
                .delete("/employee/"+appUser.getEmployee().getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token);

        MvcResult deleteResult = mockMvc.perform(deleteEmployee).andReturn();
        assertEquals(200, deleteResult.getResponse().getStatus());
    }

    // Test Update Employee
    @Test
    public void testUpdateEmployee() throws Exception {
        // add admin => just admin can add employee
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

        // add Employee
        SaveAndUpdateEmployeeRequest employee = new SaveAndUpdateEmployeeRequest("first_name" , "last_name", 5000, true,1,null);
        String jsonRequestEmployee = mapper.writeValueAsString(employee);

        RequestBuilder addEmployee = MockMvcRequestBuilders
                .post("/employee")
                .accept(MediaType.APPLICATION_JSON).content(jsonRequestEmployee)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token);

        MvcResult addResult = mockMvc.perform(addEmployee).andReturn();
        AppUser appUser =  objectMapper.readValue(addResult.getResponse().getContentAsString(),AppUser.class);

        // update employee
        SaveAndUpdateEmployeeRequest updateEmployee = new SaveAndUpdateEmployeeRequest("first_name" + ((int) (Math.random()*2000)), "last_name", 5000, false,1, appUser.getId());
        String jsonRequestEmployeeToUpdate = mapper.writeValueAsString(updateEmployee);
        RequestBuilder updateEmployeeRequest = MockMvcRequestBuilders
                .put("/employee")
                .accept(MediaType.APPLICATION_JSON).content(jsonRequestEmployeeToUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token);

        MvcResult deleteResult = mockMvc.perform(updateEmployeeRequest).andReturn();
        assertEquals(200, deleteResult.getResponse().getStatus());
    }

    // Test Search for Employee by isActive
    @Test
    public void testSearchActiveOrNotActiveEmployee() throws Exception {
        // add admin => just admin can add employee
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

        // Search for active employee
        RequestBuilder SearchForActiveReq = MockMvcRequestBuilders
                .get("/active_employee?isActive=true")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token);

        MvcResult SearchForActiveResult = mockMvc.perform(SearchForActiveReq).andReturn();
        assertEquals(200, SearchForActiveResult.getResponse().getStatus());
    }

    // Test Get All Employees
    @Test
    public void testGetAllEmployees() throws Exception {
        // add admin => just admin can add employee
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

        // get all employees
        RequestBuilder allEmployees = MockMvcRequestBuilders
                .get("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token);

        MvcResult addResult = mockMvc.perform(allEmployees).andReturn();
        assertEquals(200, addResult.getResponse().getStatus());
    }

    // Search for employees
    @Test
    public void testSearchForEmployeesByNamesOrAndDepartment() throws Exception {
        // add admin => just admin can add employee
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

        // Search for employees
        SearchEmployeeRequest searchEmployeeRequest = new SearchEmployeeRequest("areej", "obaid", null);
        String jsonSearchRequest = mapper.writeValueAsString(user);

        RequestBuilder searchForEmployees = MockMvcRequestBuilders
                .post("/search_employees")
                .accept(MediaType.APPLICATION_JSON).content(jsonSearchRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token);

        MvcResult searchForEmployeesResult = mockMvc.perform(searchForEmployees).andReturn();
        assertEquals(200, searchForEmployeesResult.getResponse().getStatus());
    }

    // Test update salary by ratio
    @Test
    public void testUpdateSalaryByRatio() throws Exception {
        // add admin => just admin can add employee
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
        System.out.println(token);

        // update salary
        RequestBuilder updateSalaryRequest = MockMvcRequestBuilders
                .put("/salary_employee?ratio=.4&employee_id=1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token);

        MvcResult updateSalaryResult = mockMvc.perform(updateSalaryRequest).andReturn();
        assertEquals(200, updateSalaryResult.getResponse().getStatus());
    }

}
