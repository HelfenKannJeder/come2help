package de.helfenkannjeder.come2help.server.rest;

import de.helfenkannjeder.come2help.server.domain.Ability;
import de.helfenkannjeder.come2help.server.rest.dto.AbilityDto;
import de.helfenkannjeder.come2help.server.service.AbilitiesService;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/abilities")
@Transactional
public class AbilitiesController {

    private final AbilitiesService abilitiesService;

    @Autowired
    public AbilitiesController(AbilitiesService abilitiesService) {
        this.abilitiesService = abilitiesService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<AbilityDto> getAbilities() {
        List<Ability> volunteers = abilitiesService.findAll();
        return volunteers.stream().map(v -> AbilityDto.createFullDto(v)).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public AbilityDto createAbility(@Valid @RequestBody AbilityDto volunteerDto) {
        Ability volunteer = AbilityDto.createAbility(volunteerDto);
        Ability createdAbility = abilitiesService.createAbility(volunteer);
        return AbilityDto.createFullDto(createdAbility);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AbilityDto getAbilityById(@PathVariable Long id) {
        Ability volunteer = abilitiesService.findById(id);
        return AbilityDto.createFullDto(volunteer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public AbilityDto updateAbility(@NotNull @PathVariable Long id, @Valid @RequestBody AbilityDto volunteerDto) {
        volunteerDto.setId(id);
        Ability volunteer = AbilityDto.createAbility(volunteerDto);
        Ability updatedAbility = abilitiesService.updateAbility(volunteer);
        return AbilityDto.createFullDto(updatedAbility);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAbility(@NotNull @PathVariable Long id) {
        abilitiesService.deleteAbility(id);
    }
}
