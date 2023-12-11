package com.ng.bci.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.ng.bci.context.PreloadServiceContextTest;
import com.ng.bci.domain.Phone;
import com.ng.bci.domain.User;
import com.ng.bci.dto.PhoneDTO;
import com.ng.bci.dto.UserDTO;
import com.ng.bci.dto.UserResponseDTO;
import com.ng.bci.exception.EmailAlreadyExistsException;
import com.ng.bci.exception.PasswordNotValidException;
import com.ng.bci.exception.UnauthorizedOperationException;
import com.ng.bci.exception.UserNotFoundException;
import com.ng.bci.service.impl.UserService;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

class UserServiceTest extends PreloadServiceContextTest {

  @Autowired
  private UserService userService;

  @Test
  void testGetUserInfo() {
    User user = buildUser();

    String name = user.getName();

    when(authentication.getName())
        .thenReturn(name);

    SecurityContextHolder.getContext().setAuthentication(authentication);

    when(userRepository.findByName(name))
        .thenReturn(Optional.of(user));

    UserDTO userDTO = userService.getUserInfo();

    assertNotNull(userDTO);
    assertEquals(user.getName(), userDTO.getName());
    assertEquals(user.getEmail(), userDTO.getEmail());
    assertEquals(user.getPassword(), userDTO.getPassword());
  }

  @Test
  void testGetUserInfoButUserNotFound() {
    String name = "Nahueinexistente";

    when(authentication.getName())
        .thenReturn(name);

    SecurityContextHolder.getContext().setAuthentication(authentication);

    when(userRepository.findByName(name))
        .thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userService.getUserInfo());
  }

  @Test
  void testSaveUser() {
    UserDTO userDTO = buildUserDTO();

    when(passwordEncoder.encode(userDTO.getPassword()))
        .thenReturn("testPasswordEncoded");

    when(jwtUtil.generateTokenWithUsername(userDTO.getName()))
        .thenReturn("testToken");

    when(userRepository.findByEmail(userDTO.getEmail()))
        .thenReturn(Optional.empty());

    UserResponseDTO responseDTO = userService.saveUser(userDTO);

    assertNotNull(responseDTO);
    assertEquals(userDTO.getName(), responseDTO.getName());
    assertEquals(userDTO.getEmail(), responseDTO.getEmail());
    assertEquals(userDTO.getPassword(), responseDTO.getPassword());
    assertEquals("testToken", responseDTO.getToken());
  }

  @Test
  void testSaveUserThrowsPasswordNotValidException() {
    UserDTO userDTO = buildUserDTO();
    userDTO.setPassword("pass");

    assertThrows(PasswordNotValidException.class, () -> userService.saveUser(userDTO));
  }

  @Test
  void testSaveUserThrowsEmailAlreadyExistsException() {
    UserDTO userDTO = buildUserDTO();

    when(userRepository.findByEmail(userDTO.getEmail()))
        .thenReturn(Optional.of(buildUser()));

    assertThrows(EmailAlreadyExistsException.class, () -> userService.saveUser(userDTO));
  }

  @Test
  void testUpdateUserWithUserFound() {
    UUID userId = UUID.randomUUID();
    UserDTO userDTO = buildUserDTO();
    userDTO.setEmail("mailUpdated@gmail.com");

    User existingUser = buildUser();
    existingUser.setId(userId);
    existingUser.setName("originalUser");

    when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

    UserResponseDTO responseDTO = userService.updateUser(userDTO, userId);

    assertNotNull(responseDTO);
    assertEquals("mailUpdated@gmail.com", responseDTO.getEmail());
    assertEquals(userId, responseDTO.getId());
  }

  @Test
  void testUpdateUserButUserNotFound() {
    UUID userId = UUID.randomUUID();
    UserDTO userDTO = new UserDTO("NahuNewUser", "ng2@gmail.com", "Nahuelito22!-", null);

    when(userRepository.findById(userId))
        .thenReturn(Optional.empty());

    when(jwtUtil.generateTokenWithUsername(userDTO.getName()))
        .thenReturn("testToken");

    UserResponseDTO responseDTO = userService.updateUser(userDTO, userId);

    assertNotNull(responseDTO);
    assertEquals("NahuNewUser", responseDTO.getName());
    assertEquals("testToken", responseDTO.getToken());
  }

  @Test
  void testDeleteUser() {
    UUID userId = UUID.randomUUID();

    when(authentication.getName())
        .thenReturn("Nahuel");

    SecurityContextHolder.getContext().setAuthentication(authentication);

    User user = buildUser();
    user.setId(userId);
    when(userRepository.findByName("Nahuel"))
        .thenReturn(Optional.of(user));

    assertDoesNotThrow(() -> userService.deleteUser(userId));
  }

  @Test
  void testDeleteUserThrowsUnauthorizedOperationException() {
    UUID userId = UUID.randomUUID();

    when(authentication.getName())
        .thenReturn("Nahuel");

    SecurityContextHolder.getContext().setAuthentication(authentication);

    User user = buildUser();
    user.setId(userId);

    when(userRepository.findByName("Nahuel"))
        .thenReturn(Optional.of(user));

    assertThrows(UnauthorizedOperationException.class, () -> userService.deleteUser(UUID.randomUUID()));
  }

  private User buildUser() {
    final UUID id = UUID.randomUUID();
    final ZonedDateTime createdAndLastLogin = ZonedDateTime.now(ZoneId.of("GMT"));

    User user = new User(id, "Nahuel", "ng@gmail.com", "Test1234-!", null, createdAndLastLogin, null,
        createdAndLastLogin, "1234", true);

    Set<Phone> phones = Collections.singleton(new Phone(1, 55443322, 11, 549, user));

    user.setPhones(phones);

    return user;
  }

  private UserDTO buildUserDTO() {
    Set<PhoneDTO> phones = new HashSet<>();
    phones.add(new PhoneDTO(55443322, 11, 549));
    return new UserDTO("Nahuel", "ng@gmail.com", "Test1234-!", phones);
  }

}
