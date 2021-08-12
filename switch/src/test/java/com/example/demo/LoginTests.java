package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.entities.AppUser;
import com.example.demo.entities.Employee;
import com.example.demo.repositories.UserRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class LoginTests {
    @Autowired
    private MockMvc mockMvc;

//
//    @MockBean
//    private UserRepository userRepository;

    String exampleUserJson = "{\"userName\":\"areej1\",\"password\":\"obaid\"}";

    @Test
    public void loginTest() throws Exception {

        AppUser mockUser = new AppUser("areej1", "obaid",null);

//        Mockito.when(
//                userRepository.findByUserNameAndPassword(Mockito.anyString(),
//                        Mockito.anyString())).thenReturn(mockUser);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/login")
//                .accept(MediaType.APPLICATION_JSON).content(exampleUserJson)
//                .contentType(MediaType.APPLICATION_JSON);
////
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        System.out.println("response");
//        System.out.println(response);
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
//
//        assertEquals("http://localhost/user/login",
//                response.getHeader(HttpHeaders.LOCATION));
//
//        MockMvcRequestBuilders
//                .get("/users/"  + "/rounds/")
//                .accept(MediaType.APPLICATION_JSON)
//                .header(HttpHeaders.AUTHORIZATION, "Bearer token").contentType(MediaType.APPLICATION_JSON);
    }

//    @Before
//    public void setUp() throws Exception {
//        initMocks(this);
//        UserController controller = new UserController();
//        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//    }
//
//    @Test
//    public void addUser() throws Exception {
//        Employee emp = new Employee(("first_name" + Math.floor(Math.random() * 100)),"emp_name",true,);//whichever data your entity class have
//
//        Mockito.when(employeeService.addEmployee(Mockito.any(Employee.class))).thenReturn(emp);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
//                        .content(asJsonString(emp))
//                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"));
//    }
//
//    public static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
