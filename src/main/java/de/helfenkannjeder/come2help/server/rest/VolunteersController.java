package de.helfenkannjeder.come2help.server.rest;

import de.helfenkannjeder.come2help.server.domain.Coordinate;
import de.helfenkannjeder.come2help.server.domain.Volunteer;
import de.helfenkannjeder.come2help.server.rest.dto.VolunteerDto;
import de.helfenkannjeder.come2help.server.service.VolunteersService;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
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

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<VolunteerDto> getVolunteers(@RequestParam @NotNull Double latitude, @RequestParam @NotNull Double longitude, @RequestParam @NotNull Double distance) {
        List<Volunteer> volunteers = volunteersService.findAllInDistance(new Coordinate(latitude, longitude), distance);
        return volunteers.stream().map(v -> VolunteerDto.createFullDto(v)).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public VolunteerDto createVolunteer(@Valid @RequestBody VolunteerDto volunteerDto) {
        Volunteer volunteer = VolunteerDto.createVolunteer(volunteerDto);
        Volunteer createdVolunteer = volunteersService.createVolunteer(volunteer);
        return VolunteerDto.createFullDto(createdVolunteer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public VolunteerDto getVolunteerById(Principal principal, @PathVariable Long id) {
        System.out.println("principal: " + principal);
        Volunteer volunteer = volunteersService.findById(id);
        return VolunteerDto.createFullDto(volunteer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public VolunteerDto updateVolunteer(@NotNull @PathVariable Long id, @Valid @RequestBody VolunteerDto volunteerDto) {
        volunteerDto.setId(id);
        Volunteer volunteer = VolunteerDto.createVolunteer(volunteerDto);
        Volunteer updatedVolunteer = volunteersService.updateVolunteer(volunteer);
        return VolunteerDto.createFullDto(updatedVolunteer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVolunteer(@NotNull @PathVariable Long id) {
        volunteersService.deleteVolunteer(id);
    }
}
