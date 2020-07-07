package org.company.hotelbooking.controller;

import java.util.List;
import org.company.hotelbooking.dto.*;
import org.company.hotelbooking.entity.User;
import org.company.hotelbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Volodymyr
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(
            value = "/all",
            method = RequestMethod.GET
    )
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(
            value = "/{id:[\\d]+}",
            method = RequestMethod.GET
    )
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);

    }

    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void createUser(@RequestBody UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        userService.createUser(user);
    }

}
