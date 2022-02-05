package hexlet.code.controller;

import hexlet.code.dto.TaskStatusDto;
import hexlet.code.entity.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.service.TaskStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
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

import static hexlet.code.controller.TaskStatusController.TASK_STATUS_CONTROLLER_PATH;
import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + TASK_STATUS_CONTROLLER_PATH)
public class TaskStatusController {
    public static final String TASK_STATUS_CONTROLLER_PATH = "/statuses";
    public static final String ID = "/{id}";

    private final TaskStatusRepository taskStatusRepository;
    private final TaskStatusService taskStatusService;

    /**
     * @param taskStatusDTO TaskStatusDto
     * @return registered task status
     */
    @Operation(summary = "Create new TaskStatus")
    @ApiResponse(responseCode = "201", description = "TaskStatus created")
    @PostMapping
    @ResponseStatus(CREATED)
    public TaskStatus createNewTaskStatus(@Parameter(description = "TaskStatus to save")
                                              @RequestBody @Valid final TaskStatusDto taskStatusDTO) {
        return taskStatusService.createNewTaskStatus(taskStatusDTO);
    }

    /**
     * @return all task statuses
     */
    @Operation(summary = "Get all TaskStatuses")
    @ApiResponses(@ApiResponse(responseCode = "200", content =
        @Content(schema = @Schema(implementation = TaskStatus.class))
        ))
    @GetMapping
    public List<TaskStatus> getAllTaskStatuses() {
        return taskStatusRepository.findAll()
                .stream()
                .toList();
    }

    /**
     * @param id
     * @return TaskStatus by ID
     */
    @Operation(summary = "Get TaskStatus by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "TaskStatus found"),
        @ApiResponse(responseCode = "404", description = "TaskStatus with that id not found")
    })
    @GetMapping(ID)
    public TaskStatus getTaskStatusById(@PathVariable final Long id) {
        return taskStatusRepository.findById(id).get();
    }

    /**
     * @param id
     * @param dto
     * @return updated User
     */
    @Operation(summary = "Update existing TaskStatus")
    @ApiResponse(responseCode = "200", description = "TaskStatus updated")
    @PutMapping(ID)
    public TaskStatus updateTaskStatus(@Parameter(description = "TaskStatus for update id") @PathVariable final long id,
                                       @Parameter(schema = @Schema(implementation = TaskStatusDto.class))
                                       @RequestBody @Valid final TaskStatusDto dto) {
        return taskStatusService.updateTaskStatus(id, dto);
    }

    /**
     * @param id
     */
    @Operation(summary = "Delete TaskStatus")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "TaskStatus deleted"),
        @ApiResponse(responseCode = "404", description = "TaskStatus with that id not found")
    })
    @DeleteMapping(ID)
    public void deleteTaskStatus(@Parameter(description = "Id of TaskStatus to be deleted")
                                     @PathVariable final long id) {
        taskStatusRepository.deleteById(id);
    }


}
