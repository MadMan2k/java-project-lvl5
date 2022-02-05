package hexlet.code.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    /**
     *
     * @return welcome
     */
    @Operation(summary = "Welcome")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Application is running"),
        @ApiResponse(responseCode = "404", description = "Application is shutted down")
    })
    @GetMapping("/welcome")
    public String getWelcome() {
        return "Welcome to Spring";
    }
}
