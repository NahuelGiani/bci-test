package com.ng.bci.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "USERS")
public class User {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "ID")
  @ColumnDefault("random_uuid()")
  private UUID id;

  private String name;

  private String email;

  private String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private Set<Phone> phones;

  private ZonedDateTime created;

  private ZonedDateTime modified;

  private ZonedDateTime lastLogin;

  private String token;

  private Boolean isActive;

  public User() {
  }

  public User(String name, String email, String password, Set<Phone> phones) {
    super();
    this.name = name;
    this.email = email;
    this.password = password;
    this.phones = phones;
  }

  public User(UUID id, String name, String email, String password, Set<Phone> phones, ZonedDateTime created, ZonedDateTime modified,
      ZonedDateTime lastLogin, String token, Boolean isActive) {
    super();
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.phones = phones;
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

  public Set<Phone> getPhones() {
    return phones;
  }

  public void setPhones(Set<Phone> phones) {
    this.phones = phones;
  }

  public ZonedDateTime getCreated() {
    return created;
  }

  public ZonedDateTime getModified() {
    return modified;
  }

  public ZonedDateTime getLastLogin() {
    return lastLogin;
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

  @PrePersist
  private void onPersiste() {
    this.created = ZonedDateTime.now();
    this.lastLogin = ZonedDateTime.now();
    this.isActive = true;
  }

  @PreUpdate
  private void onUpdate() {
    this.modified = ZonedDateTime.now();
  }

}
