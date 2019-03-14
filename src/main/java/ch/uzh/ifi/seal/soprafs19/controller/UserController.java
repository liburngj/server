package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.exceptions.NotFoundException;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.web.bind.annotation.*;
import ch.uzh.ifi.seal.soprafs19.exceptions.ExceptionLogin;

import java.util.Map;

@RestController
public class UserController {

    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    Iterable<User> all() {
        return service.getUsers();
    }

    @GetMapping("/users/{idUser}")
    User getUser(@PathVariable long idUser) {
        return service.getUser(idUser);
    }

    @PostMapping("/users")
    User createUser(@RequestBody User newUser) {
        if (service.getUserByUsername(newUser.getUsername()) != null) {
            throw new ExceptionLogin();
        } else {
            return this.service.createUser(newUser);
        }
    }
    @PostMapping("/logout/{id}")
    User logoutUser(@PathVariable long id){
        User logoutUser = service.getUser(id);
        return service.logOut(id,logoutUser);
    }

    @PostMapping("/auth")
    @ExceptionHandler({ExceptionLogin.class})
    User loginUser (@RequestBody User loginUser){ return service.checkCredentials(loginUser); }

    @CrossOrigin
    @PutMapping("/users/{idUser}")
    User updateUser(@PathVariable long idUser,@RequestBody User updatedUser){
        User anUser = this.service.getUser(idUser);
        if(anUser != null){
            return this.service.updateUser(idUser,updatedUser);

        }
        else{
            throw new NotFoundException();
        }
    }

}
