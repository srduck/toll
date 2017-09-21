package srduck.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import srduck.dao.User;

import static org.junit.Assert.*;

/**
 * Created by main on 19.09.17.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    public void findByLogin() throws Exception {

        User user = new User();
        user.setLogin("Newlogin");
        testEntityManager.persist(user);
        testEntityManager.flush();

        User newUser = userRepository.findByLogin("Newlogin");

        assertEquals(user.getLogin(),newUser.getLogin());
    }

}