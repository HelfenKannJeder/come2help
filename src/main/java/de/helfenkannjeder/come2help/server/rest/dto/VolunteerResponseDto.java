package de.helfenkannjeder.come2help.server.rest.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import de.helfenkannjeder.come2help.server.domain.Ability;
import de.helfenkannjeder.come2help.server.domain.Address;
import de.helfenkannjeder.come2help.server.domain.Volunteer;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class VolunteerResponseDto {

    private Long id;

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

    @Valid
    @NotNull(message = "not.null")
    private List<AbilityDto> abilities = Collections.emptyList();

    public VolunteerResponseDto() {
    }

    public VolunteerResponseDto(Long id, String email, String givenName, String surname, AddressDto address, String phone, Boolean adult, List<AbilityDto> abilities) {
        this.id = id;
        this.email = email;
        this.givenName = givenName;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.adult = adult;
        this.abilities = abilities;
    }

    public static VolunteerResponseDto createFullDto(Volunteer volunteer) {
        AddressDto addressDto = AddressDto.createFullDto(volunteer.getAddress());
        List<AbilityDto> abilityDtos = volunteer.getAbilities().stream().map(AbilityDto::createFullDto).collect(Collectors.toList());
        return new VolunteerResponseDto(volunteer.getId(), volunteer.getEmail(), volunteer.getGivenName(), volunteer.getSurname(),
                addressDto, volunteer.getPhone(), volunteer.isAdult(), abilityDtos);
    }

    public static Volunteer createVolunteer(VolunteerResponseDto dto) {
        Address address = AddressDto.createAddress(dto.getAddress());
        List<Ability> abilities = dto.abilities.stream().map(AbilityDto::createAbility).collect(Collectors.toList());
        return new Volunteer(dto.id, dto.email, dto.givenName, dto.surname, address, dto.phone, dto.adult, abilities);
    }

    public Long getId() {
        return id;
    }

    public VolunteerResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public VolunteerResponseDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public VolunteerResponseDto setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public VolunteerResponseDto setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public AddressDto getAddress() {
        return address;
    }

    public VolunteerResponseDto setAddress(AddressDto address) {
        this.address = address;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public VolunteerResponseDto setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Boolean isAdult() {
        return adult;
    }

    public VolunteerResponseDto setAdult(Boolean isAdult) {
        this.adult = isAdult;
        return this;
    }

    public List<AbilityDto> getAbilities() {
        return abilities;
    }

    public VolunteerResponseDto setAbilities(List<AbilityDto> abilities) {
        this.abilities = abilities;
        return this;
    }
}
