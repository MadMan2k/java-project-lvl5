package hexlet.code.service;

import hexlet.code.entity.User;

import java.util.Optional;

public interface UserAuthenticationService {

    String login(String username, String password);

    Optional<User> findByToken(String token);
}
