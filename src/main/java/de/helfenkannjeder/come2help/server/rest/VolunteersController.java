package de.helfenkannjeder.come2help.server.rest;

import de.helfenkannjeder.come2help.server.domain.Volunteer;
import de.helfenkannjeder.come2help.server.rest.dto.VolunteerDto;
import de.helfenkannjeder.come2help.server.service.VolunteersService;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/volunteers")
@Transactional
public class VolunteersController {

    private final VolunteersService volunteersService;

    @Autowired
    public VolunteersController(VolunteersService volunteersService) {
        this.volunteersService = volunteersService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public VolunteerDto getUserById(@PathVariable Long id) {
        Volunteer volunteer = volunteersService.findById(id);
        return VolunteerDto.createFullDto(volunteer);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public VolunteerDto createUser(@Valid @RequestBody VolunteerDto volunteerDto) {
        Volunteer volunteer = VolunteerDto.createVolunteer(volunteerDto);
        Volunteer createdVolunteer = volunteersService.createVolunteer(volunteer);
        return VolunteerDto.createFullDto(createdVolunteer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public VolunteerDto updateUser(@NotNull @PathVariable Long id, @Valid @RequestBody VolunteerDto volunteerDto) {
        volunteerDto.setId(id);
        Volunteer volunteer = VolunteerDto.createVolunteer(volunteerDto);
        Volunteer updatedVolunteer = volunteersService.updateVolunteer(volunteer);
        return VolunteerDto.createFullDto(updatedVolunteer);
    }
}
