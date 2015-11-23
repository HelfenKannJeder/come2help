package de.helfenkannjeder.come2help.server.rest.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import de.helfenkannjeder.come2help.server.domain.Address;
import de.helfenkannjeder.come2help.server.domain.Volunteer;
import org.hibernate.validator.constraints.Email;

public class VolunteerDto {

    private Long id;

    @Email(message = "email.not.invalid")
    private String email;

    @NotNull(message = "givenName.not.null")
    private String givenName;

    @NotNull(message = "surname.not.null")
    private String surname;

    @NotNull(message = "address.not.null")
    private AddressDto address;

    private String phone;

    @AssertTrue(message = "adult.not.false")
    private boolean adult;

    public VolunteerDto() {
    }

    public VolunteerDto(Long id, String email, String givenName, String surname, AddressDto address, String phone, boolean adult) {
        this.id = id;
        this.email = email;
        this.givenName = givenName;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.adult = adult;
    }

    public static VolunteerDto createFullDto(Volunteer volunteer) {
        AddressDto addressDto = AddressDto.createFullDto(volunteer.getAddress());
        return new VolunteerDto(volunteer.getId(), volunteer.getEmail(), volunteer.getGivenName(), volunteer.getSurname(), addressDto, volunteer.getPhone(), volunteer.isAdult());
    }

    public static Volunteer createVolunteer(VolunteerDto dto) {
        Address address = AddressDto.createAddress(dto.getAddress());
        return new Volunteer(dto.id, dto.email, dto.givenName, dto.surname, address, dto.phone, dto.adult);
    }

    public Long getId() {
        return id;
    }

    public VolunteerDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public VolunteerDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public VolunteerDto setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public VolunteerDto setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public AddressDto getAddress() {
        return address;
    }

    public VolunteerDto setAddress(AddressDto address) {
        this.address = address;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public VolunteerDto setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public boolean isAdult() {
        return adult;
    }

    public VolunteerDto setAdult(boolean isAdult) {
        this.adult = isAdult;
        return this;
    }
}
