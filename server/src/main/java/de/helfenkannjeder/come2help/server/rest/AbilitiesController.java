package de.helfenkannjeder.come2help.server.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import de.helfenkannjeder.come2help.server.domain.Ability;
import de.helfenkannjeder.come2help.server.rest.dto.AbilityDto;
import de.helfenkannjeder.come2help.server.rest.dto.AbilityResponseDto;
import de.helfenkannjeder.come2help.server.service.AbilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public List<AbilityResponseDto> getAbilities() {
        List<Ability> abilities = abilitiesService.findAllOrderByName();
        List<Ability> parentAbilities = new ArrayList<>();
        // TODO controller should not contain anny service logic
        // TODO replace findAll by findByParentAbility where parent ability is null
        for (Ability ability: abilities) {
            if (ability.getParentAbility() == null) {
                parentAbilities.add(ability);
            }
        }

        return parentAbilities.stream().map(AbilityResponseDto::createFullDto).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public AbilityResponseDto createAbility(@Valid @RequestBody AbilityDto abilityDto) {
        Ability ability = AbilityDto.createAbility(abilityDto);
        Ability createdAbility = abilitiesService.createAbility(ability);
        return AbilityResponseDto.createFullDto(createdAbility);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AbilityResponseDto getAbilityById(@PathVariable(value = "id") Long id) {
        Ability ability = abilitiesService.findById(id);
        return AbilityResponseDto.createFullDto(ability);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public AbilityResponseDto updateAbility(@NotNull @PathVariable(value = "id") Long id, @Valid @RequestBody AbilityDto abilityDto) {
        abilityDto.setId(id);
        Ability ability = AbilityDto.createAbility(abilityDto);
        Ability updatedAbility = abilitiesService.updateAbility(ability);
        return AbilityResponseDto.createFullDto(updatedAbility);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAbility(@NotNull @PathVariable(value = "id") Long id) {
        abilitiesService.deleteAbility(id);
    }
}
