package hexlet.code.app.service;

import hexlet.code.app.entity.User;

import java.util.Optional;

public interface UserAuthenticationService {

    String login(String username, String password);

    Optional<User> findByToken(String token);
}
