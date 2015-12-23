package de.helfenkannjeder.come2help.server.service;

import de.helfenkannjeder.come2help.server.domain.Ability;
import de.helfenkannjeder.come2help.server.domain.repository.AbilityRepository;
import de.helfenkannjeder.come2help.server.security.Authorities;
import de.helfenkannjeder.come2help.server.service.exception.InvalidDataException;
import de.helfenkannjeder.come2help.server.service.exception.ResourceNotFoundException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbilitiesService {

    private final AbilityRepository abilityRepository;

    @Autowired
    public AbilitiesService(AbilityRepository abilityRepository) {
        this.abilityRepository = abilityRepository;
    }

    @RolesAllowed(Authorities.USER)
    public List<Ability> findAll() {
        return abilityRepository.findAll();
    }

    @RolesAllowed(Authorities.USER)
    public Ability findById(Long id) {
        Ability ability = abilityRepository.findOne(id);
        if (ability == null) {
            throw new ResourceNotFoundException(id);
        }
        return ability;
    }

    @RolesAllowed(Authorities.C2H_ADMIN)
    public Ability createAbility(Ability ability) {
        if (ability == null) {
            throw InvalidDataException.forSingleError("ability.not.null", null);
        }
        if (ability.getId() != null) {
            throw InvalidDataException.forSingleError("ability.id.null", ability.getId().toString());
        }

        return abilityRepository.save(ability);
    }

    @RolesAllowed(Authorities.C2H_ADMIN)
    public Ability updateAbility(Ability ability) {
        if (ability == null) {
            throw InvalidDataException.forSingleError("ability.not.null", null);
        }
        if (ability.getId() == null) {
            throw InvalidDataException.forSingleError("ability.id.not.null", null);
        }

        Ability dbAbility = findById(ability.getId());
        if (dbAbility == null) {
            throw new ResourceNotFoundException(ability.getId());
        }

        dbAbility.update(ability);
        return abilityRepository.save(dbAbility);
    }

    @RolesAllowed(Authorities.C2H_ADMIN)
    public void deleteAbility(Long id) {
        if (id == null) {
            throw InvalidDataException.forSingleError("ability.id.not.null", null);
        }

        Ability ability = findById(id);
        abilityRepository.delete(ability);
    }
}
