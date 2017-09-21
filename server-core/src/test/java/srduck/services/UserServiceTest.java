package srduck.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import srduck.dao.User;
import srduck.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by main on 19.09.17.
 */
@RunWith(SpringRunner.class)
public class UserServiceTest {

    private final String userLogin = "SourceUser";
    private User sourceUser;
    private List<User> listUsers;

    @TestConfiguration
    public static class UserServiceTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {

        sourceUser = new User();
        listUsers = new ArrayList<>();
        sourceUser.setLogin(userLogin);
        for (int i = 0; i < 3; i++){
            listUsers.add(new User());
        }

        when(userRepository.findByLogin(userLogin)).thenReturn(sourceUser);
        when(userRepository.save(sourceUser)).thenReturn(sourceUser);
        when(userRepository.findAll()).thenReturn(listUsers);
        doThrow(Exception.class).when(userRepository).delete(sourceUser);
    }

    @Test
    public void create() throws Exception {
        User destUser = userService.create(sourceUser);
        assertEquals(destUser, sourceUser);
    }

    @Test
    public void update() throws Exception {
        User destUser = userService.update(sourceUser);
        assertEquals(destUser, sourceUser);
    }

    @Test(expected = Exception.class)
    public void delete() throws Exception {
        userService.delete(sourceUser);
    }

    @Test
    public void findAll() throws Exception {
        List<User> users = userService.findAll();
        assertEquals(users.size(), 3);
    }

    @Test
    public void findByLogin() throws Exception {
        User destUser = userService.findByLogin(userLogin);
        assertEquals(destUser.getLogin(), userLogin);
    }

}