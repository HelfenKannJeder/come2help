package de.helfenkannjeder.come2help.server.domain.repository;

import de.helfenkannjeder.come2help.server.domain.AbilityCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AbilityCategoryRepository extends CrudRepository<AbilityCategory, Long> {

    @Override
    public List<AbilityCategory> findAll();

    @Override
    public List<AbilityCategory> findAll(Iterable<Long> ids);
}
