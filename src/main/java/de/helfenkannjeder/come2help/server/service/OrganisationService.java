package de.helfenkannjeder.come2help.server.service;

import com.google.common.collect.Lists;
import de.helfenkannjeder.come2help.server.domain.Organisation;
import de.helfenkannjeder.come2help.server.domain.repository.OrganisationRepository;
import de.helfenkannjeder.come2help.server.service.exception.ConcurrentDeletedException;
import de.helfenkannjeder.come2help.server.service.exception.DuplicateResourceException;
import de.helfenkannjeder.come2help.server.service.exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class OrganisationService {

    private final OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    public List<Organisation> findAll() {
        return Lists.newArrayList(organisationRepository.findAll());
    }

    public Organisation findById(Long id) {
        return organisationRepository.findOne(id);
    }

    public Organisation createOrganisation(Organisation organisation) {
        if (organisation == null) {
            return organisation;
        }

        Organisation tmp = organisationRepository.findByName(organisation.getName());
        if (tmp != null) {
            throw new DuplicateResourceException(format("An organisation with name %s already exists", organisation.getName()));
        }

        return organisationRepository.save(organisation);
    }

    public Organisation updateOrganisation(Organisation organisation) {
        if (organisation == null) {
            return null;
        }

        if (organisation.getId() == null) {
            throw InvalidDataException.forSingleError("id.not.null", null);
        }

        Organisation tmp = organisationRepository.findOne(organisation.getId());
        if (tmp == null) {
            throw new ConcurrentDeletedException(organisation.getId());
        }

        return organisationRepository.save(organisation);
    }

    //TODO should we really delete organisations or to be able to track historical stuff just soft-delete/deactivate them?
    public void deleteOrganisationById(Long id) {
        Organisation organisation = organisationRepository.findOne(id);
        if (organisation == null) {
            return;
        }

        organisationRepository.delete(id);
    }

    public void deleteOrganisation(Organisation organisation) {
        if (organisation == null) {
            return;
        }

        deleteOrganisationById(organisation.getId());
    }
}
