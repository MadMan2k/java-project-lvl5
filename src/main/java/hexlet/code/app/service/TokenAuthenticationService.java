package hexlet.code.app.service;

import hexlet.code.app.entity.User;
import hexlet.code.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TokenAuthenticationService implements UserAuthenticationService {

    private final TokenService tokenService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * @param username
     * @param password
     * @return
     */
    @Override
    public String login(String username, String password) {
        return userRepository.findByEmail(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> tokenService.expiring(Map.of("username", username)))
                .orElseThrow(() -> new UsernameNotFoundException("invalid login and/or password"));
    }

    /**
     * @param token
     * @return
     */
    @Override
    public Optional<User> findByToken(String token) {
        return userRepository.findByEmail(tokenService.verify(token).get("username").toString());
    }
}
