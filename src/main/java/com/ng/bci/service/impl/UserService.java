package com.ng.bci.service.impl;

import com.ng.bci.domain.User;
import com.ng.bci.dto.UserDTO;
import com.ng.bci.dto.UserResponseDTO;
import com.ng.bci.exception.EmailAlreadyExistsException;
import com.ng.bci.exception.PasswordNotValidException;
import com.ng.bci.exception.UnauthorizedOperationException;
import com.ng.bci.exception.UserNotFoundException;
import com.ng.bci.jwt.impl.IJwtUtil;
import com.ng.bci.repository.IPhoneRepository;
import com.ng.bci.repository.IUserRepository;
import com.ng.bci.service.IUserService;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ngiani Capa de servicio encargada de lo relacionado al User
 */
@Service
public class UserService implements IUserService {

  private ModelMapper modelMapper;

  private IUserRepository userRepository;

  private IPhoneRepository phoneRepository;

  private IJwtUtil jwtUtil;

  private PasswordEncoder passwordEncoder;

  @Value("${pattern.user.password.validation}")
  public String patternValidationPassword;

  public UserService(final IUserRepository userRepository, final IPhoneRepository phoneRepository,
      final IJwtUtil jwtUtil, final PasswordEncoder passwordEncoder, final ModelMapper modelMapper) {
    this.userRepository = userRepository;
    this.phoneRepository = phoneRepository;
    this.jwtUtil = jwtUtil;
    this.modelMapper = modelMapper;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDTO getUserInfo() {
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    Optional<User> user = userRepository.findByName(name);
    if (user.isPresent()) {
      return modelMapper.map(user.get(), UserDTO.class);
    } else {
      throw new UserNotFoundException("User " + name + " not found");
    }
  }

  @Override
  public UserResponseDTO saveUser(UserDTO userDTO) {
    if (!Pattern.compile(patternValidationPassword).matcher(userDTO.getPassword()).find()) {
      throw new PasswordNotValidException("The password does not match the required pattern: " + patternValidationPassword);
    }
    userRepository.findByEmail(userDTO.getEmail())
        .ifPresent(user -> {
          throw new EmailAlreadyExistsException("The email already exists in database");
        });

    String token = jwtUtil.generateTokenWithUsername(userDTO.getName());
    userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    return saveOrUpdate(userDTO, token);
  }

  @Override
  public UserResponseDTO updateUser(UserDTO userDTO, UUID userId) {
    Optional<User> userFound = userRepository.findById(userId);

    if (userFound.isPresent()) {
      User user = userFound.get();
      modelMapper.map(userDTO, user);
      return saveOrUpdate(user);
    } else {
      String token = jwtUtil.generateTokenWithUsername(userDTO.getName());
      return saveOrUpdate(userDTO, token);
    }
  }

  @Override
  public void deleteUser(UUID userId) {
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    Optional<User> user = userRepository.findByName(name);
    if (user.isPresent() && user.get().getId().equals(userId)) {
      userRepository.deleteById(userId);
    } else {
      throw new UnauthorizedOperationException("You cannot operate with a user other than yours");
    }
  }

  private UserResponseDTO saveOrUpdate(UserDTO userDTO, String token) {
    User user = modelMapper.map(userDTO, User.class);
    user.setToken(token);
    return saveOrUpdate(user);
  }

  private UserResponseDTO saveOrUpdate(User user) {
    userRepository.save(user);
    if (user.getPhones() != null && !user.getPhones().isEmpty()) {
      user.getPhones().stream().forEach(phone -> phone.setUser(user));
      phoneRepository.saveAll(user.getPhones());
    }
    return modelMapper.map(user, UserResponseDTO.class);
  }

}
