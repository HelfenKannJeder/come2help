package de.helfenkannjeder.come2help.server.rest;

import de.helfenkannjeder.come2help.server.domain.Ability;
import de.helfenkannjeder.come2help.server.rest.dto.AbilityDto;
import de.helfenkannjeder.come2help.server.rest.dto.AbilityResponseDto;
import de.helfenkannjeder.come2help.server.service.AbilitiesService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
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
    public List<AbilityResponseDto> getAbilities() {
        List<Ability> abilities = abilitiesService.findAll();
        List<Ability> parentAbilities = new ArrayList<>();
        // TODO replace findAll by findByParentAbility where parent ability is null
        for (Ability ability: abilities) {
            if (ability.getParentAbility() == null) {
                parentAbilities.add(ability);
            }
        }

        return parentAbilities.stream().map(v -> AbilityResponseDto.createFullDto(v)).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiErrors(apierrors = {@ApiError(code = "400", description = "Bad Request"), @ApiError(code = "500", description = "Internal Server Error")})
    public AbilityResponseDto createAbility(@Valid @RequestBody AbilityDto abilityDto) {
        Ability ability = AbilityDto.createAbility(abilityDto);
        Ability createdAbility = abilitiesService.createAbility(ability);
        return AbilityResponseDto.createFullDto(createdAbility);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiErrors(apierrors = {@ApiError(code = "500", description = "Internal Server Error")})
    public AbilityResponseDto getAbilityById(@PathVariable(value = "id") Long id) {
        Ability ability = abilitiesService.findById(id);
        return AbilityResponseDto.createFullDto(ability);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public AbilityResponseDto updateAbility(@Valid @RequestBody AbilityDto abilityDto) {
        Ability ability = AbilityDto.createAbility(abilityDto);
        Ability updatedAbility = abilitiesService.updateAbility(ability);
        return AbilityResponseDto.createFullDto(updatedAbility);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiErrors(apierrors = {@ApiError(code = "500", description = "Internal Server Error")})
    public void deleteAbility(@NotNull @PathVariable(value = "id") Long id) {
        abilitiesService.deleteAbility(id);
    }
}
