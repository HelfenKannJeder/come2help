package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.domain.Ability;
import de.helfenkannjeder.come2help.server.domain.User;
import de.helfenkannjeder.come2help.server.domain.Volunteer;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class VolunteerResponseDto {

    private Long id;

    @Valid
    @NotNull(message = "not.null")
    private UserDto user;

    @Valid
    @NotNull(message = "not.null")
    private List<AbilityDto> abilities = Collections.emptyList();

    public VolunteerResponseDto() {
    }

    public VolunteerResponseDto(Long id, UserDto user, List<AbilityDto> abilities) {
        this.id = id;
        this.user = user;
        this.abilities = abilities;
    }

    public static VolunteerResponseDto createFullDto(Volunteer volunteer) {
        UserDto user = UserDto.createFullDto(volunteer.getUser());
        List<AbilityDto> abilityDtos = volunteer.getAbilities().stream().map(AbilityDto::createFullDto).collect(Collectors.toList());
        return new VolunteerResponseDto(volunteer.getId(), user, abilityDtos);
    }

    public static Volunteer createVolunteer(VolunteerResponseDto dto) {
        User user = UserDto.createUser(dto.user);
        List<Ability> abilities = dto.abilities.stream().map(AbilityDto::createAbility).collect(Collectors.toList());
        return new Volunteer(dto.id, user, abilities);
    }

    public Long getId() {
        return id;
    }

    public VolunteerResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public UserDto getUser() {
        return user;
    }

    public VolunteerResponseDto setUser(UserDto user) {
        this.user = user;
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
