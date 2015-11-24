package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.domain.Ability;
import de.helfenkannjeder.come2help.server.domain.Address;
import de.helfenkannjeder.come2help.server.domain.Volunteer;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
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

    @NotNull(message = "adult.not.null")
    @AssertTrue(message = "adult.not.false")
    private Boolean adult;

    @NotNull
    private List<AbilityDto> abilities = Collections.emptyList();

    public VolunteerDto() {
    }

    public VolunteerDto(Long id, String email, String givenName, String surname, AddressDto address, String phone, Boolean adult, List<AbilityDto> abilities) {
        this.id = id;
        this.email = email;
        this.givenName = givenName;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.adult = adult;
        this.abilities = abilities;
    }

    public static VolunteerDto createFullDto(Volunteer volunteer) {
        AddressDto addressDto = AddressDto.createFullDto(volunteer.getAddress());
        List<AbilityDto> abilityDtos = volunteer.getAbilities().stream().map(a -> AbilityDto.createFullDto(a)).collect(Collectors.toList());
        return new VolunteerDto(volunteer.getId(), volunteer.getEmail(), volunteer.getGivenName(), volunteer.getSurname(),
                addressDto, volunteer.getPhone(), volunteer.isAdult(), abilityDtos);
    }

    public static Volunteer createVolunteer(VolunteerDto dto) {
        Address address = AddressDto.createAddress(dto.getAddress());
        List<Ability> abilities = dto.abilities.stream().map(a -> AbilityDto.createAbility(a)).collect(Collectors.toList());
        return new Volunteer(dto.id, dto.email, dto.givenName, dto.surname, address, dto.phone, dto.adult, abilities);
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

    public Boolean isAdult() {
        return adult;
    }

    public VolunteerDto setAdult(Boolean isAdult) {
        this.adult = isAdult;
        return this;
    }

    public List<AbilityDto> getAbilities() {
        return abilities;
    }

    public VolunteerDto setAbilities(List<AbilityDto> abilities) {
        this.abilities = abilities;
        return this;
    }
}
