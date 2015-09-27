package de.helfenkannjeder.come2help.server.domain.repository;

import de.helfenkannjeder.come2help.server.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}