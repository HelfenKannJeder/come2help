package de.helfenkannjeder.come2help.server.service;

import java.util.List;
import java.util.stream.Collectors;

import de.helfenkannjeder.come2help.server.domain.Coordinate;
import de.helfenkannjeder.come2help.server.domain.Volunteer;
import de.helfenkannjeder.come2help.server.domain.repository.AbilityRepository;
import de.helfenkannjeder.come2help.server.domain.repository.VolunteerRepository;
import de.helfenkannjeder.come2help.server.service.exception.InvalidDataException;
import de.helfenkannjeder.come2help.server.service.exception.ResourceNotFoundException;
import de.helfenkannjeder.come2help.server.util.DistanceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VolunteersService {

    private final VolunteerRepository volunteerRepository;
    private final AbilityRepository abilityRepository;

    @Autowired
    public VolunteersService(VolunteerRepository volunteerRepository, AbilityRepository abilityRepository) {
        this.volunteerRepository = volunteerRepository;
        this.abilityRepository = abilityRepository;
    }

    public List<Volunteer> findAll() {
        return volunteerRepository.findAll();
    }

    public List<Volunteer> findAllInDistance(final Coordinate coordinate, Double distance) {
        List<Volunteer> all = findAll();

        return all.parallelStream().filter(v -> DistanceCalculator.getDistanceFor(v.getAddress().getCoordinate(), coordinate) <= distance).collect(Collectors.toList());
    }

    public Volunteer findById(Long id) {
        Volunteer volunteer = volunteerRepository.findOne(id);
        if (volunteer == null) {
            throw new ResourceNotFoundException(id);
        }
        return volunteer;
    }

    public Volunteer createVolunteer(Volunteer volunteer) {
        if (volunteer == null) {
            throw InvalidDataException.forSingleError("volunteer.not.null", null);
        }
        if (volunteer.getId() != null) {
            throw InvalidDataException.forSingleError("volunteer.id.null", volunteer.getId().toString());
        }
        loadAbilities(volunteer);

        return volunteerRepository.save(volunteer);
    }

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
