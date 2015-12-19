package de.helfenkannjeder.come2help.server.rest;

import de.helfenkannjeder.come2help.server.domain.Ability;
import de.helfenkannjeder.come2help.server.rest.dto.AbilityDto;
import de.helfenkannjeder.come2help.server.service.AbilitiesService;
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
@ApiErrors(apierrors = @ApiError(code = "500", description = "Internal Server Error"))
public class AbilitiesController {

    private final AbilitiesService abilitiesService;

    @Autowired
    public AbilitiesController(AbilitiesService abilitiesService) {
        this.abilitiesService = abilitiesService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<AbilityDto> getAbilities() {
        List<Ability> abilities = abilitiesService.findAll();
        return abilities.stream().map(AbilityDto::createFullDto).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public AbilityDto createAbility(@Valid @RequestBody AbilityDto abilityDto) {
        Ability ability = AbilityDto.createAbility(abilityDto);
        Ability createdAbility = abilitiesService.createAbility(ability);
        return AbilityDto.createFullDto(createdAbility);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AbilityDto getAbilityById(@PathVariable(value = "id") Long id) {
        Ability ability = abilitiesService.findById(id);
        return AbilityDto.createFullDto(ability);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public AbilityDto updateAbility(@NotNull @PathVariable(value = "id") Long id, @Valid @RequestBody AbilityDto abilityDto) {
        abilityDto.setId(id);
        Ability ability = AbilityDto.createAbility(abilityDto);
        Ability updatedAbility = abilitiesService.updateAbility(ability);
        return AbilityDto.createFullDto(updatedAbility);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAbility(@NotNull @PathVariable(value = "id") Long id) {
        abilitiesService.deleteAbility(id);
    }
}
