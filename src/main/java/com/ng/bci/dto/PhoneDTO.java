package com.ng.bci.dto;

public class PhoneDTO {

  private Integer number;
  private Integer cityCode;
  private Integer countryCode;

  public PhoneDTO() {
  }

  public PhoneDTO(Integer number, Integer cityCode, Integer countryCode) {
    super();
    this.number = number;
    this.cityCode = cityCode;
    this.countryCode = countryCode;
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

}
