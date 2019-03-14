package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.exceptions.ExceptionLogin;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ch.uzh.ifi.seal.soprafs19.exceptions.ExceptionLogin;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }
    public User getUser(long aidiUser){ return this.userRepository.findById(aidiUser); }
    public User getUserByUsername (String usernameUser) {return this.userRepository.findByUsername(usernameUser);}

    public User createUser(User newUser) {

       // Assert.assertNull(userRepository.findByUsername("testUsername"));

        //User testUser = new User();
       // testUser.setName("testName");
      //  testUser.setUsername("testUsername");

      //  User createdUser = userService.createUser(testUser);

       // Assert.assertNotNull(createdUser.getToken());
      //  Assert.assertEquals(createdUser.getStatus(),UserStatus.ONLINE);
       // Assert.assertEquals(createdUser, userRepository.findByToken(createdUser.getToken()));
        newUser.creationDate =  LocalDate.now();
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User checkCredentials(User loginUser) throws ExceptionLogin {
    User targetUser = this.userRepository.findByUsername(loginUser.getUsername());
        if (targetUser != null) {
            if (targetUser.getPassword().equals(loginUser.getPassword())) {
                targetUser.setStatus(UserStatus.ONLINE);
                return targetUser;
            } else {
                throw new ExceptionLogin();
            }
        } else {
            throw new ExceptionLogin();
        }
    }

    public User logOut(long idUser, User logOutUser){
        logOutUser.setStatus(UserStatus.OFFLINE);
        return logOutUser;
    }

    public User updateUser(long idUser,User updatedUser){
        User checkUser = this.userRepository.findByUsername(updatedUser.getUsername());
        User anUser = getUser(idUser);
        if(checkUser!= null){
            throw new ExceptionLogin();
        }

        if(anUser.getUsername() != updatedUser.getUsername() && updatedUser.getUsername() != null){
            anUser.setUsername(updatedUser.getUsername());
        }

        if(anUser.getBirthday() != updatedUser.getBirthday() && updatedUser.getBirthday() != null){
            anUser.SetBirthday(updatedUser.getBirthday());
        }

        userRepository.save(anUser); //what if not?
        return anUser;

    }









}