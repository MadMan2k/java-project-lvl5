package hexlet.code.controller;

import hexlet.code.dto.UserDto;
import hexlet.code.entity.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.UserAuthenticationService;
import hexlet.code.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static hexlet.code.controller.UserController.USER_CONTROLLER_PATH;
import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + USER_CONTROLLER_PATH)
public class UserController {
    public static final String USER_CONTROLLER_PATH = "/users";
    public static final String ID = "/{id}";

    private static final String ONLY_OWNER_BY_ID = """
            @userRepository.findById(#id).get().getEmail() == authentication.getName()
        """;

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserAuthenticationService authenticationService;

    /**
     * @param dto UserDTO
     * @return registered user
     */
    @Operation(summary = "Create new user")
    @ApiResponse(responseCode = "201", description = "User created")
    @PostMapping
    @ResponseStatus(CREATED)
    public User registerNew(@Parameter(description = "User to save") @RequestBody @Valid final UserDto dto) {
        return userService.createNewUser(dto);
    }

    /**
     * @return all users
     */
    @Operation(summary = "Get all users")
    // Content используется для укзания содержимого ответа
    @ApiResponses(@ApiResponse(responseCode = "200", content =
            // Указываем тип содержимого ответа
        @Content(schema = @Schema(implementation = User.class))
        ))
    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll()
                .stream()
                .toList();
    }

    /**
     * @param id
     * @return searched User
     */
    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User with that id not found")
    })
    @GetMapping(ID)
    public User getUserById(@PathVariable final Long id) {
        return userRepository.findById(id).get();
    }

    /**
     * @param id
     * @param dto
     * @return updated User
     */
    @Operation(summary = "Update existing user")
    @ApiResponse(responseCode = "200", description = "User updated")
    @PutMapping(ID)
    @PreAuthorize(ONLY_OWNER_BY_ID)
    public User update(@Parameter(description = "User for update id") @PathVariable final long id,
                       @Parameter(schema = @Schema(implementation = UserDto.class))
                       @RequestBody @Valid final UserDto dto) {
        return userService.updateUser(id, dto);
    }

    /**
     * @param id
     */
    @Operation(summary = "Delete user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User deleted"),
        @ApiResponse(responseCode = "404", description = "User with that id not found")
    })
    @DeleteMapping(ID)
    @PreAuthorize(ONLY_OWNER_BY_ID)
    public void delete(@Parameter(description = "Id of user to be deleted") @PathVariable final long id) {
        userRepository.deleteById(id);
    }
}
