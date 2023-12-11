package com.ng.bci.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.ng.bci.context.PreloadGeneralContextTest;
import com.ng.bci.dto.PhoneDTO;
import com.ng.bci.dto.UserDTO;
import com.ng.bci.dto.UserResponseDTO;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

class UserControllerTest extends PreloadGeneralContextTest {

  @Autowired
  private Gson gson;

  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  void testGetUsersList() throws Exception {
        when(userService.getUserInfo()).thenReturn(new UserDTO());

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

  @Test
  void testSaveUser() throws Exception {
    UserDTO userDTO = buildUserDTO();
    UserResponseDTO responseDTO = buildResponseDTO();

    when(userService.saveUser(any())).thenReturn(responseDTO);

    mockMvc.perform(MockMvcRequestBuilders.post("/users")
        .content(gson.toJson(userDTO))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(responseDTO.getId().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(responseDTO.getName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email", is(responseDTO.getEmail())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.password", is(responseDTO.getPassword())));
  }

  @Test
  void testUpdateUser() throws Exception {
    UserDTO userDTO = buildUserDTO();

    UserResponseDTO userResponseDTO = buildResponseDTO();

    UUID userId = userResponseDTO.getId();

    when(userService.updateUser(any(), any())).thenReturn(userResponseDTO);

    mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", userId)
        .content(gson.toJson(userDTO))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userResponseDTO.getId().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(userResponseDTO.getName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email", is(userResponseDTO.getEmail())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.password", is(userResponseDTO.getPassword())));
  }

  @Test
  void testDeleteUser() throws Exception {
    UUID someUserId = UUID.randomUUID();

    doNothing().when(userService).deleteUser(any());

    mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", someUserId))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  private UserDTO buildUserDTO() {
    Set<PhoneDTO> phones = new HashSet<>();
    phones.add(new PhoneDTO(55443322, 11, 549));
    return new UserDTO("Nahuel", "ng@gmail.com", "Test1234-!", phones);
  }

  private UserResponseDTO buildResponseDTO() {
    final ZonedDateTime createdAndLastLogin = ZonedDateTime.now(ZoneId.of("GMT"));
    return new UserResponseDTO("Nahuel", "ng@gmail.com", "Test1234-!", null, UUID.randomUUID(), createdAndLastLogin, null,
        createdAndLastLogin, "1234", true);
  }

}
