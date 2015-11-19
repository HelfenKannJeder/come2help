package de.helfenkannjeder.come2help.server.rest.dto;

import javax.validation.constraints.AssertTrue;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;

public class VolunteerDto {

    private Long id;

    @Email
    private String email;
    @NotNull
    private String givenName;
    @NotNull
    private String surname;
    private AddressDto address;
    private String phone;
    @AssertTrue
    private boolean isAdult;

    public VolunteerDto() {
    }

    public VolunteerDto(String email, String givenName, String surname, AddressDto address, String phone, boolean isAdult) {
        this.email = email;
        this.givenName = givenName;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.isAdult = isAdult;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isIsAdult() {
        return isAdult;
    }

    public void setIsAdult(boolean isAdult) {
        this.isAdult = isAdult;
    }

}
