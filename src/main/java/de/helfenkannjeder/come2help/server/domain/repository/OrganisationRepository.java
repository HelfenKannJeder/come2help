package de.helfenkannjeder.come2help.server.domain.repository;

import de.helfenkannjeder.come2help.server.domain.Organisation;
import org.springframework.data.repository.CrudRepository;

public interface OrganisationRepository extends CrudRepository<Organisation, Long> {

    Organisation findByName(String name);
}