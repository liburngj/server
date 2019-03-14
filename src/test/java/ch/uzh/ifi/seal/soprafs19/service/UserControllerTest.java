package ch.uzh.ifi.seal.soprafs19.service;
import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.controller.UserController;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
@AutoConfigureMockMvc
public class UserControllerTest {



    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void fetchAllUsersWithInvalidToken() throws Exception {
        this.mvc.perform(get("/users/1").header("authorization", "fake_token")).andDo(print()).andExpect(status().is(403));
    }

    @Test
    public void fetchUserWithInvalidToken() throws Exception {
        this.mvc.perform(get("/users/2").header("authorization", "fake_token")).andDo(print()).andExpect(status().is(403));
    }

    @Test
    public void createUser() throws Exception {
        // User testUser = new User();
        // testUser.setUsername("testUser");
        // testUser.setPassword("testPassword");
        //  userService.createUser(testUser);

        this.mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"testUser\", \"password\": \"testPassword\"}"))
                .andExpect(status().is(201)) //andDo(print()).
                .andExpect(jsonPath("$.id", notNullValue()));

        userRepository.delete(userRepository.findByUsername("testUser"));


    }

    private void andExpect(JsonPathResultMatchers jsonPath, Matcher<Object> notNullValue) {
    }

    @Test
    public void loginUser() throws Exception {
        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setUsername("Test User");
        testUser.setPassword("testPassword");
        User path = userService.createUser(testUser);

        this.mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"testUser\", \"password\": \"testPassword\"}"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.token", notNullValue()));

        userRepository.delete(userRepository.findByUsername("testUser"));

    }

    @Test
    public void updateUser() throws Exception {
        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setUsername("Test User");
        testUser.setPassword("testPassword");
        User path = userService.createUser(testUser);

        this.mvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", testUser.getToken())
                .content("{\"username\": \"testUserUpdated\", \"password\": \"testPasswordUpdated\"}"))
                .andExpect(status().is(200)); //andDo(print()).
        userRepository.delete(userRepository.findByUsername("testUserUpdated"));

    }
    @Test
    public void getUser() throws Exception{
        User testUser = new User();
        testUser.setUsername("testUser2");
        testUser.setPassword("testPassword2");
        userService.createUser(testUser);

        this.mvc.perform(get("/users/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)); //andDo(print()).
    }




}
