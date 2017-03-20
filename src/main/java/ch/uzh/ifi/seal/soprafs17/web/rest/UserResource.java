package ch.uzh.ifi.seal.soprafs17.web.rest;

import java.util.ArrayList;
import java.util.List;

import ch.uzh.ifi.seal.soprafs17.service.UserService;
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
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.repository.UserRepository;


@RestController
@RequestMapping(UserResource.CONTEXT)
public class UserResource extends GenericResource {

    Logger logger = LoggerFactory.getLogger(UserResource.class);

    static final String CONTEXT = "/users";
    public int counter = 1;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<User> listUsers() {
        logger.debug("listUsers");
        return userService.listUsers();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public User addUser(@RequestBody User user) {
        logger.debug("addUser: " + user);
        String token =""+counter++;
        user = userService.createUser(user.getName(),user.getUsername(), token, UserStatus.ONLINE, null);
        return user;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable Long userId) {
        logger.debug("getUser: " + userId);
        return userService.getUser(userId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/{userId}/login")
    @ResponseStatus(HttpStatus.OK)
    public User login(@PathVariable Long userId) {
        logger.debug("login: " + userId);
            return userService.login(userId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/{userId}/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@PathVariable Long userId, @RequestParam("token") String userToken) {
        logger.debug("getUser: " + userId);
        userService.logout(userId,userToken);
    }
}
