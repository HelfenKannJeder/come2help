package de.helfenkannjeder.come2help.server.domain.repository;

import de.helfenkannjeder.come2help.server.domain.Volunteer;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface VolunteerRepository extends CrudRepository<Volunteer, Long> {

    @Override
    public List<Volunteer> findAll();
}
