package de.helfenkannjeder.come2help.server.service;

import com.google.common.collect.Lists;
import de.helfenkannjeder.come2help.server.domain.Address;
import de.helfenkannjeder.come2help.server.domain.User;
import de.helfenkannjeder.come2help.server.domain.repository.UserRepository;
import de.helfenkannjeder.come2help.server.service.exception.ConcurrentDeletedException;
import de.helfenkannjeder.come2help.server.service.exception.DuplicateResourceException;
import de.helfenkannjeder.come2help.server.service.exception.InvalidDataException;
import de.helfenkannjeder.come2help.server.util.googleapi.GeoCodeCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = enrichInformation(user);

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


        //TODO how about password? Should not be hashed again. Only if it was changed! Special api method for this?
//        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = enrichInformation(user);

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

    /**
     * enrich information which calculated ones (address: lat, lgn)
     * @param user
     * @return
     */
    private User enrichInformation(User user) {
        Address enrichedAddress = GeoCodeCaller.enrichAddressWithLatAndLgn(user.getAddress());
        user.setAddress(enrichedAddress);
        return user;
    }
}
