package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service class for managing users.
 */
@Service("userService")
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public User createUser(String name, String username, String token, UserStatus status, List<Game> games) {

        User newUser = new User();
        newUser.setName(name);
        newUser.setUsername(username);
        newUser.setToken(token);
        newUser.setStatus(status);
        newUser.setGames(games);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }


    public void deleteUser(Long id) {
        User user = userRepository.findById(id);
        if (user != null) {
            userRepository.delete(id);
            log.debug("Deleted User: {}", user);
        }
    }

    public Iterable<User> listUsers(){
        return userRepository.findAll();
    }

    public User getUser(long userId){
        return userRepository.findById(userId);
    }

    public User login(long userId){
        User user = userRepository.findOne(userId);
        if (user != null) {
            user.setStatus(UserStatus.ONLINE);
            user = userRepository.save(user);
            return user;
        }
        return null;
    }

    public User logout(long userId, String userToken){
        User user = userRepository.findOne(userId);
        if (user != null && user.getToken().equals(userToken)) {
            user.setStatus(UserStatus.OFFLINE);
            user = userRepository.save(user);
            return user;
        }
        return null;
    }
}
