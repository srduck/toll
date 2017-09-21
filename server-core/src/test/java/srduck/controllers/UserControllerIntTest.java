package srduck.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import srduck.MainServerCore;
import srduck.dao.User;
import srduck.services.UserService;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = MainServerCore.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class UserControllerIntTest {

    private User testUser;
    private String jsonString;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        testUser = new User();
        testUser.setLogin("Test Login");
        testUser.setPassword("Test Password");
        ObjectMapper mapper = new ObjectMapper();
        jsonString = mapper.writeValueAsString(testUser);
    }

    @Test
    public void getAllUsers() throws Exception {

        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));

    }

    @Test
    public void deleteUser() throws Exception {

        mockMvc.perform(post("/deleteUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\":\"" + testUser.getLogin() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message",is("Запись успешно удалена")));
    }

    @Test
    public void createUser() throws Exception {

        mockMvc.perform(post("/createUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                //.andDo(print())
                .andExpect(jsonPath("$.login", is(testUser.getLogin())))
                .andExpect(jsonPath("$.password", is(testUser.getPassword())));
    }


    @Test
    public void updateUser() throws Exception {

        mockMvc.perform(post("/editUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login", is(testUser.getLogin())))
                .andExpect(jsonPath("$.password", is(testUser.getPassword())));
    }

}
