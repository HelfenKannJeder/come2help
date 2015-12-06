package de.helfenkannjeder.come2help.server.service;

import de.helfenkannjeder.come2help.server.domain.AbilityCategory;
import de.helfenkannjeder.come2help.server.domain.repository.AbilityCategoryRepository;
import de.helfenkannjeder.come2help.server.service.exception.InvalidDataException;
import de.helfenkannjeder.come2help.server.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbilityCategoriesService {

    private final AbilityCategoryRepository abilityCategoryRepository;

    @Autowired
    public AbilityCategoriesService(AbilityCategoryRepository abilityRepository) {
        this.abilityCategoryRepository = abilityRepository;
    }

    public List<AbilityCategory> findAll() {
        return abilityCategoryRepository.findAll();
    }

    public AbilityCategory findById(Long id) {
        AbilityCategory abilityCategory = abilityCategoryRepository.findOne(id);
        if (abilityCategory == null) {
            throw new ResourceNotFoundException(id);
        }
        return abilityCategory;
    }

    public AbilityCategory createAbilityCategory(AbilityCategory abilityCategory) {
        if (abilityCategory == null) {
            throw InvalidDataException.forSingleError("abilityCategory.not.null", null);
        }
        if (abilityCategory.getId() != null) {
            throw InvalidDataException.forSingleError("abilityCategory.id.null", abilityCategory.getId().toString());
        }

        return abilityCategoryRepository.save(abilityCategory);
    }

    public AbilityCategory updateAbilityCategory(AbilityCategory abilityCategory) {
        if (abilityCategory == null) {
            throw InvalidDataException.forSingleError("abilityCategory.not.null", null);
        }
        if (abilityCategory.getId() == null) {
            throw InvalidDataException.forSingleError("abilityCategory.id.not.null", null);
        }

        AbilityCategory dbAbilityCategory = findById(abilityCategory.getId());
        if (dbAbilityCategory == null) {
            throw new ResourceNotFoundException(abilityCategory.getId());
        }

        dbAbilityCategory.update(abilityCategory);
        return abilityCategoryRepository.save(dbAbilityCategory);
    }

    public void deleteAbilityCategory(Long id) {
        if (id == null) {
            throw InvalidDataException.forSingleError("abilityCategory.id.not.null", null);
        }

        AbilityCategory abilityCategory = findById(id);
        abilityCategoryRepository.delete(abilityCategory);
    }
}
