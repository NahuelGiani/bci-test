package com.ng.bci.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

public class UserDTO {

  @NotNull(message = "The name cannot be null")
  private String name;

  @Email(
      message = "The email address must be a correct pattern",
      regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")
  @NotNull(message = "The email cannot be null")
  private String email;

  @NotNull(message = "The password cannot be null")
  private String password;

  private Set<PhoneDTO> phones;

  public UserDTO() {
  }

  public UserDTO(String name, String email, String password, Set<PhoneDTO> phones) {
    super();
    this.name = name;
    this.email = email;
    this.password = password;
    this.phones = phones;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<PhoneDTO> getPhones() {
    return phones;
  }

  public void setPhones(Set<PhoneDTO> phones) {
    this.phones = phones;
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, name, password, phones);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    UserDTO other = (UserDTO) obj;
    return Objects.equals(email, other.email) && Objects.equals(name, other.name) && Objects.equals(password, other.password)
        && Objects.equals(phones, other.phones);
  }

}
