package de.helfenkannjeder.come2help.server.rest;

import de.helfenkannjeder.come2help.server.domain.AbilityCategory;
import de.helfenkannjeder.come2help.server.rest.dto.AbilityCategoryDto;
import de.helfenkannjeder.come2help.server.service.AbilityCategoriesService;
import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/abilitycategories")
@Transactional
public class AbilityCategoryController {

    private final AbilityCategoriesService abilityCategoriesService;

    @Autowired
    public AbilityCategoryController(AbilityCategoriesService abilityCategoriesService) {
        this.abilityCategoriesService = abilityCategoriesService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<AbilityCategoryDto> getAbilityCategories() {
        List<AbilityCategory> abilityCategories = abilityCategoriesService.findAll();
        return abilityCategories.stream().map(v -> AbilityCategoryDto.createFullDto(v)).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiErrors(apierrors = {@ApiError(code = "400", description = "Bad Request"), @ApiError(code = "500", description = "Internal Server Error")})
    public AbilityCategoryDto createAbilityCategory(@Valid @RequestBody AbilityCategoryDto abilityCategoryDto) {
        AbilityCategory abilityCategory = AbilityCategoryDto.createAbilityCategory(abilityCategoryDto);
        AbilityCategory createdAbilityCategory = abilityCategoriesService.createAbilityCategory(abilityCategory);
        return AbilityCategoryDto.createFullDto(createdAbilityCategory);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiErrors(apierrors = {@ApiError(code = "500", description = "Internal Server Error")})
    public AbilityCategoryDto getAbilityCategoryById(@PathVariable(value = "id") Long id) {
        AbilityCategory abilityCategory = abilityCategoriesService.findById(id);
        return AbilityCategoryDto.createFullDto(abilityCategory);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public AbilityCategoryDto updateAbilityCategory(@NotNull @PathVariable(value = "id") Long id, @Valid @RequestBody AbilityCategoryDto abilityCategoryDto) {
        abilityCategoryDto.setId(id);
        AbilityCategory abilityCategory = AbilityCategoryDto.createAbilityCategory(abilityCategoryDto);
        AbilityCategory updatedAbilityCategory = abilityCategoriesService.updateAbilityCategory(abilityCategory);
        return AbilityCategoryDto.createFullDto(updatedAbilityCategory);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiErrors(apierrors = {@ApiError(code = "500", description = "Internal Server Error")})
    public void deleteAbilityCategory(@NotNull @PathVariable(value = "id") Long id) {
        abilityCategoriesService.deleteAbilityCategory(id);
    }
}
