package hexlet.code.app.controller;

import hexlet.code.app.dto.LoginDto;
import hexlet.code.app.service.UserAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static hexlet.code.app.controller.AuthController.LOGIN;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + LOGIN)
public class AuthController {

    public static final String LOGIN = "/login";

    private final UserAuthenticationService authenticationService;

    /**
     * @param loginDto
     * @return token
     */
    @Operation(summary = "User authentication")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User authenticated"),
        @ApiResponse(responseCode = "403", description = "Access denied or wrong login/password")
    })
    @PostMapping
    public String login(@Parameter(schema = @Schema(implementation = LoginDto.class))
                            @RequestBody final LoginDto loginDto) {
        return authenticationService.login(loginDto.getEmail(), loginDto.getPassword());
    }
}
