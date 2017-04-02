package ch.uzh.ifi.seal.soprafs17.web.rest;

import java.util.ArrayList;
import java.util.List;

import ch.uzh.ifi.seal.soprafs17.model.DTOs.UserDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.Move;
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
    public Iterable<UserDTO> listUsers() {
        logger.debug("listUsers");
        Iterable<User> users = userService.listUsers();
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User u : users){
            List<Long> gamesId = new ArrayList<>();
            List<Long> movesId = new ArrayList<>();
            for(Game g : u.getGames()){
                gamesId.add(g.getId());
            }
            for(Move m : u.getMoves()){
                movesId.add(m.getId());
            }
            //UserDTO(Long id, String name, String username, String token, UserStatus status, List<Long> games, List<Long> moves, String color)
            usersDTO.add(new UserDTO(u.getId(),u.getName(),u.getUsername(),u.getToken(),u.getStatus(),gamesId,movesId,u.getColor()));
        }
        return usersDTO;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public User addUser(@RequestBody User user) {
        logger.debug("addUser: " + user);
        String token =""+counter++;
        User u = userService.createUser(user.getName(),user.getUsername(), token, UserStatus.ONLINE, null);
//        List<Long> gamesId = new ArrayList<>();
//        List<Long> movesId = new ArrayList<>();
//        for(Game g : u.getGames()){
//            gamesId.add(g.getId());
//        }
//        for(Move m : u.getMoves()){
//            movesId.add(m.getId());
//        }
//        return new UserDTO(u.getId(),u.getName(),u.getUsername(),u.getToken(),u.getStatus(),gamesId,movesId,u.getColor());
        return u;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(@PathVariable Long userId) {
        logger.debug("getUser: " + userId);
        User u = userService.getUser(userId);
        List<Long> gamesId = new ArrayList<>();
        List<Long> movesId = new ArrayList<>();
        for(Game g : u.getGames()){
            gamesId.add(g.getId());
        }
        for(Move m : u.getMoves()){
            movesId.add(m.getId());
        }
        return new UserDTO(u.getId(),u.getName(),u.getUsername(),u.getToken(),u.getStatus(),gamesId,movesId,u.getColor());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/{userId}/login")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO login(@PathVariable Long userId) {
        logger.debug("login: " + userId);
        User u =userService.login(userId);
        List<Long> gamesId = new ArrayList<>();
        List<Long> movesId = new ArrayList<>();
        for(Game g : u.getGames()){
            gamesId.add(g.getId());
        }
        for(Move m : u.getMoves()){
            movesId.add(m.getId());
        }
        return new UserDTO(u.getId(),u.getName(),u.getUsername(),u.getToken(),u.getStatus(),gamesId,movesId,u.getColor());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/{userId}/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@PathVariable Long userId, @RequestParam("token") String userToken) {
        logger.debug("getUser: " + userId);
        userService.logout(userId,userToken);
    }
}
