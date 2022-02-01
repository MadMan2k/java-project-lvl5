package hexlet.code.app.controller;

import com.querydsl.core.types.Predicate;
import hexlet.code.app.dto.TaskDto;
import hexlet.code.app.entity.Task;
import hexlet.code.app.repository.TaskRepository;
import hexlet.code.app.service.TaskService;
import java.util.List;
import javax.validation.Valid;
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

import static hexlet.code.app.controller.TaskController.TASK_CONTROLLER_PATH;
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
     * @return List of tasks
     */
//    @Operation(summary = "Get All posts")
    @GetMapping
    public Iterable<Task> getAll(@QuerydslPredicate(root = Task.class) Predicate predicate) {
        return taskRepository.findAll(predicate);
    }

    /**
     * @param id
     * @return task
     */
//    @Operation(summary = "Get post by Id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "post found"),
//            @ApiResponse(responseCode = "404", description = "post with that id not found")
//    })
    @GetMapping(ID)
    public Task getById(@PathVariable final Long id) {
        return taskRepository.findById(id).get();
    }

    /**
     * @param dto
     * @return task
     */
//    @Operation(summary = "Create new post")
//    @ApiResponse(responseCode = "201", description = "post created")
    @PostMapping
    @ResponseStatus(CREATED)
    public Task createNewTask(@RequestBody @Valid final TaskDto dto) {
        return taskService.createNewTask(dto);
    }

    /**
     * @param id
     * @param dto
     * @return task
     */
//    @Operation(summary = "Update post")
//    @ApiResponse(responseCode = "200", description = "post updated")
    @PutMapping(ID)
    @PreAuthorize(ONLY_AUTHOR_BY_ID)
    public Task updateTask(@PathVariable final Long id,
//                           // Schema используется, чтобы указать тип данных для параметра
//                           @Parameter(schema = @Schema(implementation = PostDto.class))
                           @RequestBody @Valid  final TaskDto dto) {
        return taskService.updateTask(id, dto);
    }

    /**
     * @param id
     */
//    @Operation(summary = "Delete post")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "post deleted"),
//            @ApiResponse(responseCode = "404", description = "post with that id not found")
//    })
    @DeleteMapping(ID)
    @PreAuthorize(ONLY_AUTHOR_BY_ID)
    public void deleteTask(@PathVariable final Long id) {
        taskRepository.deleteById(id);
    }
}
