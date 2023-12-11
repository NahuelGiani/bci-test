package com.ng.bci.service;

import com.ng.bci.dto.UserDTO;
import com.ng.bci.dto.UserResponseDTO;
import java.util.UUID;

public interface IUserService {

  public UserDTO getUserInfo();

  public UserResponseDTO saveUser(UserDTO user);

  public UserResponseDTO updateUser(UserDTO user, UUID userId);

  public void deleteUser(UUID userId);

}
