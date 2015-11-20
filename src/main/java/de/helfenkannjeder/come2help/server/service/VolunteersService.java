package de.helfenkannjeder.come2help.server.service;

import de.helfenkannjeder.come2help.server.domain.Address;
import de.helfenkannjeder.come2help.server.domain.Volunteer;
import de.helfenkannjeder.come2help.server.domain.repository.VolunteerRepository;
import de.helfenkannjeder.come2help.server.service.exception.InvalidDataException;
import de.helfenkannjeder.come2help.server.service.exception.ResourceNotFoundException;
import de.helfenkannjeder.come2help.server.util.googleapi.GeoCodeCaller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VolunteersService {

    private final VolunteerRepository volunteerRepository;

    @Autowired
    public VolunteersService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public List<Volunteer> findAll() {
        return volunteerRepository.findAll();
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
            throw InvalidDataException.forSingleError("volunteer.id.null", null);
        }

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

    /**
     * enrich information which calculated ones (address: lat, lgn)
     *
     * @param volunteer
     * @return
     */
    private Volunteer enrichInformation(Volunteer volunteer) {
        Address enrichedAddress = GeoCodeCaller.enrichAddressWithLatitudeAndLongitude(volunteer.getAddress());
        volunteer.setAddress(enrichedAddress);
        return volunteer;
    }
}
