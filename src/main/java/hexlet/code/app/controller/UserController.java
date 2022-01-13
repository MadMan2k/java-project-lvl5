package hexlet.code.app.controller;

import hexlet.code.app.entity.User;
import hexlet.code.app.repository.UserRepository;
import hexlet.code.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static hexlet.code.app.controller.UserController.USER_CONTROLLER_PATH;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + USER_CONTROLLER_PATH)
public class UserController {
    public static final String USER_CONTROLLER_PATH = "/users";
    public static final String ID = "/{id}";

//    private static final String ONLY_OWNER_BY_ID = """
//            @userRepository.findById(#id).get().getEmail() == authentication.getName()
//        """;

    private final UserService userService;
    private final UserRepository userRepository;
//    private final UserAuthenticationService authenticationService;


//    @Operation(summary = "Create new user")
//    @ApiResponse(responseCode = "201", description = "User created")
//    @PostMapping
//    @ResponseStatus(CREATED)
//    public String registerNew(@RequestBody @Valid final UserDto dto) {
//        userService.createNewUser(dto);
//        return authenticationService.login(dto.getEmail(), dto.getPassword());
//    }

    /**
     * @return all users
     */
//    // Content используется для укзания содержимого ответа
//    @ApiResponses(@ApiResponse(responseCode = "200", content =
//            // Указываем тип содержимого ответа
//    @Content(schema = @Schema(implementation = User.class))
//    ))
    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll()
                .stream()
                .toList();
    }
}