package srduck.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import srduck.dao.User;
import srduck.services.PointDTOService;
import srduck.services.RoleService;
import srduck.services.UserService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by main on 20.09.17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private User testUser;
    private String jsonString;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PointDTOService pointDTOService;

    @MockBean
    private RoleService roleService;

    @MockBean
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

        List<User> users = Arrays.asList(testUser);
        given(userService.findAll()).willReturn(users);

        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].login", is(testUser.getLogin())));

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

        //need equals and hashCode
        given(userService.create(testUser)).willReturn(testUser);

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

        given(userService.update(testUser)).willReturn(testUser);

        mockMvc.perform(post("/editUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login", is(testUser.getLogin())))
                .andExpect(jsonPath("$.password", is(testUser.getPassword())));
    }

}