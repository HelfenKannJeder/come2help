package de.helfenkannjeder.come2help.server.service;

import de.helfenkannjeder.come2help.server.domain.Coordinate;
import de.helfenkannjeder.come2help.server.domain.User;
import de.helfenkannjeder.come2help.server.domain.Volunteer;
import de.helfenkannjeder.come2help.server.domain.repository.AbilityRepository;
import de.helfenkannjeder.come2help.server.domain.repository.VolunteerRepository;
import de.helfenkannjeder.come2help.server.security.AuthenticationFacade;
import de.helfenkannjeder.come2help.server.security.Authorities;
import de.helfenkannjeder.come2help.server.security.UserAuthentication;
import de.helfenkannjeder.come2help.server.service.exception.DuplicateResourceException;
import de.helfenkannjeder.come2help.server.service.exception.InvalidDataException;
import de.helfenkannjeder.come2help.server.service.exception.ResourceNotFoundException;
import de.helfenkannjeder.come2help.server.util.DistanceCalculator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VolunteersService {

    private final VolunteerRepository volunteerRepository;
    private final AbilityRepository abilityRepository;
    private final UserService userService;

    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public VolunteersService(VolunteerRepository volunteerRepository, AbilityRepository abilityRepository, AuthenticationFacade authenticationFacade, UserService userService) {
        this.volunteerRepository = volunteerRepository;
        this.abilityRepository = abilityRepository;
        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
    }

    @RolesAllowed(Authorities.ORGANISATION_ADMIN)
    public List<Volunteer> findAllInDistance(final Coordinate coordinate, Double distance) {
        List<Volunteer> all = volunteerRepository.findAll();

        return all.parallelStream().filter(v -> DistanceCalculator.getDistanceFor(v.getUser().getAddress().getCoordinate(), coordinate) <= distance).collect(Collectors.toList());
    }

    @RolesAllowed(Authorities.ORGANISATION_ADMIN)
    public Volunteer findById(Long id) {
        Volunteer volunteer = volunteerRepository.findOne(id);
        if (volunteer == null) {
            throw new ResourceNotFoundException(id);
        }
        return volunteer;
    }

    @RolesAllowed(Authorities.GUEST)
    public Volunteer createVolunteer(Volunteer volunteer) {
        if (volunteer == null) {
            throw InvalidDataException.forSingleError("volunteer.not.null", null);
        }
        if (volunteer.getId() != null) {
            throw InvalidDataException.forSingleError("volunteer.id.null", volunteer.getId().toString());
        }
        if (volunteer.getUser().getId() != null) {
            throw InvalidDataException.forSingleError("volunteer.user.id.null", volunteer.getUser().getId().toString());
        }

        UserAuthentication authentication = authenticationFacade.getAuthentication();
        User dbUser = userService.getUserIfExists(authentication);
        if (dbUser != null) {
            if (dbUser.getVolunteer() != null) {
                throw new DuplicateResourceException("user already has a volunteer"); // TODO supply better error message
            } else {
                dbUser.update(volunteer.getUser());
                volunteer.setUser(dbUser);
            }
        }

        if (userService.existsEmail(volunteer.getUser().getEmail())) {
            throw new DuplicateResourceException("email already exists"); // TODO supply better error message
        }

        loadAbilities(volunteer);

        User user = volunteer.getUser();
        user.setAuthProvider(authentication.getAuthProvider());
        user.setExternalId(authentication.getExternalId());
        user.setVolunteer(volunteer);

        return volunteerRepository.save(volunteer);
    }

    @RolesAllowed(Authorities.VOLUNTEER)
    public Volunteer updateVolunteer(Volunteer volunteer) {
        if (volunteer == null) {
            throw InvalidDataException.forSingleError("volunteer.not.null", null);
        }
        if (volunteer.getId() == null) {
            throw InvalidDataException.forSingleError("volunteer.id.not.null", null);
        }

        Volunteer dbVolunteer = findById(volunteer.getId());
        if (dbVolunteer == null) {
            throw new ResourceNotFoundException(volunteer.getId());
        }

        loadAbilities(volunteer);
        dbVolunteer.update(volunteer);
        return volunteerRepository.save(dbVolunteer);
    }

    @RolesAllowed(Authorities.VOLUNTEER)
    public void deleteVolunteer(Long id) {
        if (id == null) {
            throw InvalidDataException.forSingleError("volunteer.id.not.null", null);
        }

        Volunteer volunteer = findById(id);
        volunteerRepository.delete(volunteer);
    }

    private void loadAbilities(Volunteer volunteer) {
        List<Long> ids = volunteer.getAbilities().stream().map(a -> a.getId()).collect(Collectors.toList());
        volunteer.setAbilities(abilityRepository.findAll(ids));
    }
}
