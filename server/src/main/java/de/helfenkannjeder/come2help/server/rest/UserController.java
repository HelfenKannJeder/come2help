package de.helfenkannjeder.come2help.server.rest;

import de.helfenkannjeder.come2help.server.domain.User;
import de.helfenkannjeder.come2help.server.rest.dto.UserDto;
import de.helfenkannjeder.come2help.server.security.AuthenticationFacade;
import de.helfenkannjeder.come2help.server.security.Authorities;
import de.helfenkannjeder.come2help.server.security.UserAuthentication;
import de.helfenkannjeder.come2help.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;

@RestController
@RequestMapping("users/")
@Transactional
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @RolesAllowed(value = {Authorities.USER})
    @RequestMapping(value = "current", method = RequestMethod.GET)
    public UserDto user() {
        final UserAuthentication authentication = authenticationFacade.getAuthentication();
        User user = userService.findUser(authentication);
        return UserDto.createFullDto(user);
    }
}
