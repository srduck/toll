package srduck.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import srduck.dao.Role;
import srduck.exceptions.MyTestException;
import srduck.repositories.RoleRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class RoleServiceTest {

    private Role role;
    private List<Role> list;
    private final String newRole = "New Role";


    @TestConfiguration
    static class roleServiceTestConfiguration {
        @Bean
        public RoleService roleService(){
            return new RoleService();
        }
    }

    @Autowired
    RoleService roleService;

    @MockBean
    RoleRepository roleRepository;


    @Before
    public void setUp() throws Exception {
        role = new Role();
        role.setName(newRole);
        list = new ArrayList<>();
        list.add(role);
        for (int i = 0; i < 3; i++){
            list.add(new Role());
        }
        when(roleRepository.findAll()).thenReturn(list);
        when(roleRepository.save(role)).thenReturn(role);
        doThrow(MyTestException.class).when(roleRepository).delete(role);
    }

     @Test
    public void create() throws Exception {
        Role newRole = roleService.create(role);
        assertEquals(newRole, role);
    }

    @Test
    public void update() throws Exception {
        Role updateRole = roleService.update(role);
        assertEquals(updateRole, role);
    }

    @Test(expected = MyTestException.class)
    public void delete() throws Exception {
        roleService.delete(role);
    }

    @Test
    public void findAll() throws Exception {
        List<Role> resultList = roleService.findAll();
        assertTrue(resultList.size() == 4);
        assertEquals(resultList.get(0), role);
    }

}