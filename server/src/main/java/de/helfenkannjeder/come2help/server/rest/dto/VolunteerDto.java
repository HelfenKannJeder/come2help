package de.helfenkannjeder.come2help.server.rest.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import de.helfenkannjeder.come2help.server.domain.Ability;
import de.helfenkannjeder.come2help.server.domain.User;
import de.helfenkannjeder.come2help.server.domain.Volunteer;

public class VolunteerDto {

    private Long id;

    @Valid
    @NotNull(message = "not.null")
    private UserDto user;

    @Valid
    private List<AbilityLinkDto> abilities = Collections.emptyList();

    public VolunteerDto() {
    }

    public VolunteerDto(Long id, UserDto user, List<AbilityLinkDto> abilities) {
        this.id = id;
        this.user = user;
        this.abilities = abilities;
    }

    public static VolunteerDto createFullDto(Volunteer volunteer) {
        UserDto user = UserDto.createFullDto(volunteer.getUser());
        List<AbilityLinkDto> abilityDtos = volunteer.getAbilities().stream().map(AbilityLinkDto::createFullDto).collect(Collectors.toList());
        return new VolunteerDto(volunteer.getId(), user, abilityDtos);
    }

    public static Volunteer createVolunteer(VolunteerDto dto) {
        User user = UserDto.createUser(dto.user);
        List<Ability> abilities = dto.abilities.stream().map(AbilityLinkDto::createAbility).collect(Collectors.toList());
        return new Volunteer(dto.id, user, abilities);
    }

    public Long getId() {
        return id;
    }

    public VolunteerDto setId(Long id) {
        this.id = id;
        return this;
    }

    public UserDto getUser() {
        return user;
    }

    public VolunteerDto setUser(UserDto user) {
        this.user = user;
        return this;
    }

    public List<AbilityLinkDto> getAbilities() {
        return abilities;
    }

    public VolunteerDto setAbilities(List<AbilityLinkDto> abilities) {
        this.abilities = abilities;
        return this;
    }
}
