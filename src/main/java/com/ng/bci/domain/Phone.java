package com.ng.bci.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "PHONES")
public class Phone {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer number;

  @Column(name = "CITY_CODE")
  private Integer cityCode;

  @Column(name = "COUNTRY_CODE")
  private Integer countryCode;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private User user;

  public Phone() {
  }

  public Phone(Integer id, Integer number, Integer cityCode, Integer countryCode, User user) {
    super();
    this.id = id;
    this.number = number;
    this.cityCode = cityCode;
    this.countryCode = countryCode;
    this.user = user;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public Integer getCityCode() {
    return cityCode;
  }

  public void setCityCode(Integer cityCode) {
    this.cityCode = cityCode;
  }

  public Integer getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(Integer countryCode) {
    this.countryCode = countryCode;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public int hashCode() {
    return Objects.hash(cityCode, countryCode, id, number, user);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Phone other = (Phone) obj;
    return Objects.equals(cityCode, other.cityCode) && Objects.equals(countryCode, other.countryCode) && Objects.equals(id, other.id)
        && Objects.equals(number, other.number) && Objects.equals(user, other.user);
  }

}
