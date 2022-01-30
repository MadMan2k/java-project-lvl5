package hexlet.code.app.controller;

import hexlet.code.app.dto.TaskStatusDTO;
import hexlet.code.app.entity.TaskStatus;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.service.TaskStatusService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

import static hexlet.code.app.controller.TaskStatusController.TASK_STATUS_CONTROLLER_PATH;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + TASK_STATUS_CONTROLLER_PATH)
public class TaskStatusController {
    public static final String TASK_STATUS_CONTROLLER_PATH = "/statuses";
    public static final String ID = "/{id}";

    private final TaskStatusRepository taskStatusRepository;
    private final TaskStatusService taskStatusService;

    /**
     * @param taskStatusDTO TaskStatusDTO
     * @return registered task status
     */
//    @Operation(summary = "Create new user")
//    @ApiResponse(responseCode = "201", description = "User created")
    @PostMapping
//    @ResponseStatus(CREATED)
    public TaskStatus createNew(@RequestBody @Valid final TaskStatusDTO taskStatusDTO) {
//        return authenticationService.login(dto.getEmail(), dto.getPassword());
        return taskStatusService.createNewTaskStatus(taskStatusDTO);
    }

    /**
     * @return all task statuses
     */
//    // Content используется для укзания содержимого ответа
//    @ApiResponses(@ApiResponse(responseCode = "200", content =
//            // Указываем тип содержимого ответа
//    @Content(schema = @Schema(implementation = User.class))
//    ))
    @GetMapping
    public List<TaskStatus> getAll() {
        return taskStatusRepository.findAll()
                .stream()
                .toList();
    }


}
