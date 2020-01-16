package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/profile")
@Api
public class ProfileController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    @ApiOperation(value = "Return the current user's profile")
    public User getCurrentUserProfile() throws UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext()
            .getAuthentication();
        return userRepository.findByUsername(auth.getName())
            .orElseThrow(UserNotFoundException::new);
    }
}
