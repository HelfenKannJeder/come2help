package de.helfenkannjeder.come2help.server.domain.repository;

import de.helfenkannjeder.come2help.server.domain.Ability;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface AbilityRepository extends CrudRepository<Ability, Long> {

    @Override
    public List<Ability> findAll();

    @Override
    public List<Ability> findAll(Iterable<Long> ids);
}
