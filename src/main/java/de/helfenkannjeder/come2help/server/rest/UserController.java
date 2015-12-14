package de.helfenkannjeder.come2help.server.rest;

import de.helfenkannjeder.come2help.server.domain.User;
import de.helfenkannjeder.come2help.server.security.UserAuthentication;
import de.helfenkannjeder.come2help.server.rest.dto.UserDto;
import de.helfenkannjeder.come2help.server.security.AuthenticationFacade;
import de.helfenkannjeder.come2help.server.service.UserService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users/")
@Transactional
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @RequestMapping(value = "current", method = RequestMethod.GET)
    public UserDto user() {
        final UserAuthentication authentication = authenticationFacade.getAuthentication();
        User user = userService.findUser(authentication);
        return UserDto.createFullDto(user);
    }
}
