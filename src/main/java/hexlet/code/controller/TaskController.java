package hexlet.code.controller;

import com.querydsl.core.types.Predicate;
import hexlet.code.dto.TaskDto;
import hexlet.code.entity.Task;
import hexlet.code.repository.TaskRepository;
import hexlet.code.service.TaskService;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
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

import static hexlet.code.controller.TaskController.TASK_CONTROLLER_PATH;
import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + TASK_CONTROLLER_PATH)
public class TaskController {

    public static final String TASK_CONTROLLER_PATH = "/tasks";
    public static final String ID = "/{id}";
    public static final String BY = "/by";

    private static final String ONLY_AUTHOR_BY_ID = """
            @taskRepository.findById(#id).get().getAuthor().getEmail() == authentication.getName()
        """;

    private final TaskRepository taskRepository;
    private final TaskService taskService;

    /**
     * @param dto
     * @return task
     */
    @Operation(summary = "Create new task")
    @ApiResponse(responseCode = "201", description = "task created")
    @PostMapping
    @ResponseStatus(CREATED)
    public Task createNewTask(@Parameter(description = "Task to save") @RequestBody @Valid final TaskDto dto) {
        return taskService.createNewTask(dto);
    }

    /**
     * @param predicate
     * @return all tasks
     */
    @Operation(summary = "Get all tasks")
    @ApiResponses(@ApiResponse(responseCode = "200", content =
        @Content(schema = @Schema(implementation = Task.class))
        ))
    @GetMapping
    public Iterable<Task> getAll(@Parameter(description = "Optional filtration of search")
                                     @QuerydslPredicate(root = Task.class) Predicate predicate) {
        return taskRepository.findAll(predicate);
    }

    /**
     * @param id
     * @return task
     */
    @Operation(summary = "Get task by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Task found"),
        @ApiResponse(responseCode = "404", description = "Task with that id not found")
    })
    @GetMapping(ID)
    public Task getById(@PathVariable final Long id) {
        return taskRepository.findById(id).get();
    }

    /**
     * @param id
     * @param dto
     * @return task
     */
    @Operation(summary = "Update existing task")
    @ApiResponse(responseCode = "200", description = "Task updated")
    @PutMapping(ID)
    @PreAuthorize(ONLY_AUTHOR_BY_ID)
    public Task updateTask(@Parameter(description = "Task for update id") @PathVariable final Long id,
                           @Parameter(schema = @Schema(implementation = TaskDto.class))
                           @RequestBody @Valid  final TaskDto dto) {
        return taskService.updateTask(id, dto);
    }

    /**
     * @param id
     */
    @Operation(summary = "Delete task")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Task deleted"),
        @ApiResponse(responseCode = "404", description = "Task with that id not found")
    })
    @DeleteMapping(ID)
    @PreAuthorize(ONLY_AUTHOR_BY_ID)
    public void deleteTask(@Parameter(description = "Id of task to be deleted") @PathVariable final Long id) {
        taskRepository.deleteById(id);
    }
}
