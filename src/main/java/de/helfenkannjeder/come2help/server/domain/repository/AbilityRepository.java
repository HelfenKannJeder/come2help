package de.helfenkannjeder.come2help.server.domain.repository;

import java.util.List;

import de.helfenkannjeder.come2help.server.domain.Ability;
import org.springframework.data.repository.CrudRepository;

public interface AbilityRepository extends CrudRepository<Ability, Long> {

    public List<Ability> findAllByOrderByName();

    @Override
    public List<Ability> findAll(Iterable<Long> ids);
}
