package com.ng.bci.dto;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class UserResponseDTO extends UserDTO {

  private UUID id;
  private ZonedDateTime created;
  private ZonedDateTime modified;
  private ZonedDateTime lastLogin;
  private String token;
  private Boolean isActive;

  public UserResponseDTO() {
    super();
  }

  public UserResponseDTO(String name, String email, String password, Set<PhoneDTO> phones, UUID id,
      ZonedDateTime created, ZonedDateTime modified, ZonedDateTime lastLogin, String token, Boolean isActive) {
    super(name, email, password, phones);
    this.id = id;
    this.created = created;
    this.modified = modified;
    this.lastLogin = lastLogin;
    this.token = token;
    this.isActive = isActive;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public ZonedDateTime getCreated() {
    return created;
  }

  public void setCreated(ZonedDateTime created) {
    this.created = created;
  }

  public ZonedDateTime getModified() {
    return modified;
  }

  public void setModified(ZonedDateTime modified) {
    this.modified = modified;
  }

  public ZonedDateTime getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(ZonedDateTime lastLogin) {
    this.lastLogin = lastLogin;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Objects.hash(created, id, isActive, lastLogin, modified, token);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    UserResponseDTO other = (UserResponseDTO) obj;
    return Objects.equals(created, other.created) && Objects.equals(id, other.id) && Objects.equals(isActive, other.isActive)
        && Objects.equals(lastLogin, other.lastLogin) && Objects.equals(modified, other.modified) && Objects.equals(token, other.token);
  }

}
