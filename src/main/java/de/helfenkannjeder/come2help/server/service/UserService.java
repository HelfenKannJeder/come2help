package de.helfenkannjeder.come2help.server.service;

import com.google.common.collect.Lists;
import de.helfenkannjeder.come2help.server.domain.User;
import de.helfenkannjeder.come2help.server.domain.repository.UserRepository;
import de.helfenkannjeder.come2help.server.security.UserAuthentication;
import de.helfenkannjeder.come2help.server.service.exception.ConcurrentDeletedException;
import de.helfenkannjeder.come2help.server.service.exception.DuplicateResourceException;
import de.helfenkannjeder.come2help.server.service.exception.InvalidDataException;
import de.helfenkannjeder.come2help.server.service.exception.ResourceNotFoundException;
import static java.lang.String.format;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return Lists.newArrayList(userRepository.findAll());
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    public User createUser(User user) {
        if (user == null) {
            return user;
        }

        User tmp = userRepository.findByEmail(user.getEmail());
        if (tmp != null) {
            throw new DuplicateResourceException(format("An user with email %s already exists", user.getEmail()));
        }

        return userRepository.save(user);
    }

    public User updateUser(User user) {
        if (user == null) {
            return null;
        }

        if (user.getId() == null) {
            throw InvalidDataException.forSingleError("id.not.null", null);
        }

        User tmp = userRepository.findOne(user.getId());
        if (tmp == null) {
            throw new ConcurrentDeletedException(user.getId());
        }

        return userRepository.save(user);
    }

    //TODO should we really delete users or to be able to track historical stuff just soft-delete/deactivate them?
    public void deleteUserById(Long id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            return;
        }

        userRepository.delete(id);
    }

    public void deleteUser(User user) {
        if (user == null) {
            return;
        }

        deleteUserById(user.getId());
    }

    public User findUser(UserAuthentication authentication) {
        User user = getUserIfExists(authentication);
        if (user == null) {
            throw new ResourceNotFoundException(authentication.getAuthProvider() + "-" + authentication.getExternalId());
        }
        return user;
    }

    public User getUserIfExists(UserAuthentication authentication) {
        return userRepository.findByAuthProviderAndExternalId(authentication.getAuthProvider(), authentication.getExternalId());
    }

    boolean existsEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
