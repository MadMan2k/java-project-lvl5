package hexlet.code.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    /**
     *
     * @return welcome
     */
    @GetMapping("/welcome")
    public String getWelcome() {
        return "Welcome to Spring";
    }
}
