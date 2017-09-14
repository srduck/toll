package srduck.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import srduck.dao.User;
import srduck.services.UserService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8092", maxAge = 3600)
public class UserController {
    private static Logger log = LoggerFactory.getLogger(TrackerController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value="/users", produces ="application/json")
    public List<User> getAllUsers(){
         return  userService.findAll();
    }

    @RequestMapping(value="/deleteUser", method = RequestMethod.POST)
    public String deleteUser (@RequestBody String jsonLogin){
        try {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(jsonLogin, User.class);
            userService.delete(user);
            return "{\"message\":\"Запись успешно удалена\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value="/createUser", method = RequestMethod.POST)
    public User createUser(@RequestBody String jsonUser){
        try {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(jsonUser, User.class);
            return userService.create(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value="/editUser", method = RequestMethod.POST)
    public User updateUser(@RequestBody String jsonUser){
        try {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(jsonUser, User.class);
            return userService.update(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
