package ch.uzh.ifi.seal.soprafs17.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ch.uzh.ifi.seal.soprafs17.DTOs.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.entity.User;
import ch.uzh.ifi.seal.soprafs17.repository.UserRepository;


@RestController
@RequestMapping(UserResource.CONTEXT)
public class UserResource
        extends GenericResource {

    Logger logger = LoggerFactory.getLogger(UserResource.class);


    static final String CONTEXT = "/users";

    @Autowired
    private UserRepository userRepo;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> listUsers() {
        logger.debug("listUsers");
        //User user = new User();

        List<UserDTO> userDTOS = new ArrayList<>();
        for (User u : userRepo.findAll()) {
            UserDTO userDTO = new UserDTO(u.getName(), u.getToken(), u.getUsername(), u.getColor());
            userDTOS.add(userDTO);
        }

        /**
         List<User> result = new ArrayList<>();
         //userRepo.findAll().forEach(result::add);
         for (User u: userRepo.findAll()) {
         result.add(u);
         }
         */

        return userDTOS;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public User addUser(@RequestBody User user) {
        logger.debug("addUser: " + user);

        user.setStatus(UserStatus.OFFLINE);
        user.setToken(UUID.randomUUID().toString());
        user = userRepo.save(user);

        return user;
        //u = userRepo.save(user);
        //return new UserDTO(u.getName(), u.getToken(), u.getUsername(), u.getColor());
    }


    @RequestMapping(method = RequestMethod.GET, value = "user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(@PathVariable Long userId) {
        logger.debug("getUser: " + userId);
        User u = userRepo.findById(userId);
        UserDTO userDTO = new UserDTO(u.getName(),u.getToken(),u.getUsername(),u.getColor());

        return userDTO;
        //return userRepo.findOne(userId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "user/{userId}/login")
    @ResponseStatus(HttpStatus.OK)
    public User login(@PathVariable Long userId) {
        logger.debug("login: " + userId);
        User user = userRepo.findOne(userId);
        if (user != null) {
            user.setToken(UUID.randomUUID().toString());
            user.setStatus(UserStatus.ONLINE);
            user = userRepo.save(user);

            return user;
        }

        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "{userId}/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@PathVariable Long userId, @RequestParam("token") String userToken) {
        logger.debug("getUser: " + userId);

        User user = userRepo.findOne(userId);

        if (user != null && user.getToken().equals(userToken)) {
            user.setStatus(UserStatus.OFFLINE);
            userRepo.save(user);
        }
    }
}
