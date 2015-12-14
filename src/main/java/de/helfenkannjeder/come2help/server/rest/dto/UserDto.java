package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.domain.Address;
import de.helfenkannjeder.come2help.server.domain.User;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserDto {

    @Email(message = "not.invalid")
    private String email;

    @NotEmpty(message = "not.empty")
    private String givenName;

    @NotEmpty(message = "not.empty")
    private String surname;

    @Valid
    @NotNull(message = "not.null")
    private AddressDto address;

    private String phone;

    @NotNull(message = "not.null")
    @AssertTrue(message = "not.false")
    private Boolean adult;

    public UserDto() {
    }

    public UserDto(String email, String givenName, String surname, AddressDto address, String phone, Boolean adult) {
        this.email = email;
        this.givenName = givenName;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.adult = adult;
    }

    public static UserDto createFullDto(User user) {
        AddressDto addressDto = AddressDto.createFullDto(user.getAddress());
        return new UserDto(user.getEmail(), user.getGivenName(), user.getSurname(), addressDto, user.getPhone(), true);
    }

    static User createUser(UserDto user) {
        Address address = AddressDto.createAddress(user.getAddress());
        return new User(user.email, user.givenName, user.surname, user.phone, address);
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public UserDto setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public UserDto setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public AddressDto getAddress() {
        return address;
    }

    public UserDto setAddress(AddressDto address) {
        this.address = address;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserDto setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Boolean getAdult() {
        return adult;
    }

    public UserDto setAdult(Boolean adult) {
        this.adult = adult;
        return this;
    }

}
