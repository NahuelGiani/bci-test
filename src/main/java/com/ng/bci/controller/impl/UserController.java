package com.ng.bci.controller.impl;

import com.ng.bci.controller.IUserController;
import com.ng.bci.dto.UserDTO;
import com.ng.bci.dto.UserResponseDTO;
import com.ng.bci.service.IUserService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author ngiani Controller principal de la aplicación. Recibe las peticiones relacionadas con el User.
 */
@RestController
public class UserController implements IUserController {

  private IUserService userService;

  public UserController(final IUserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  public ResponseEntity<UserDTO> getUsersList() {
    return new ResponseEntity<>(userService.getUserInfo(), HttpStatus.OK);
  }

  @PostMapping("/users")
  public ResponseEntity<UserResponseDTO> saveUser(@Valid @RequestBody UserDTO user) {
    return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UserDTO user, @Valid @PathVariable("id") UUID userId) {
    return new ResponseEntity<>(userService.updateUser(user, userId), HttpStatus.OK);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<?> deleteUserById(@PathVariable("id") UUID userId) {
    userService.deleteUser(userId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
