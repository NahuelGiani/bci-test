package com.ng.bci.controller;

import com.ng.bci.dto.UserDTO;
import com.ng.bci.dto.UserResponseDTO;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IUserController {

  public ResponseEntity<UserDTO> getUsersList();

  public ResponseEntity<UserResponseDTO> saveUser(@Valid @RequestBody UserDTO user);

  public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UserDTO user, @PathVariable("id") UUID userId);

  public ResponseEntity<?> deleteUserById(@PathVariable("id") UUID userId);

}
