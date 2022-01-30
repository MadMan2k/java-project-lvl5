package hexlet.code.app.service;

import hexlet.code.app.dto.TaskDto;
import hexlet.code.app.entity.Task;
import hexlet.code.app.entity.User;
import hexlet.code.app.repository.TaskRepository;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TaskStatusRepository taskStatusRepository;

    /**
     * @param taskDTO
     * @return
     */
    @Override
    public Task createNewTask(TaskDto taskDTO) {
        final Task newTask = fromDto(taskDTO);

        return taskRepository.save(newTask);
    }

    /**
     * @param id
     * @param taskDTO
     * @return
     */
    @Override
    public Task updateTask(final long id, final TaskDto taskDTO) {
        final Task task = taskRepository.findById(id).get();

        merge(task, taskDTO);

        return taskRepository.save(task);
    }

    private void merge(Task task, TaskDto taskDTO) {
        final Task newTask = fromDto(taskDTO);

        task.setName(newTask.getName());
        task.setDescription(newTask.getDescription());
        task.setExecutor(newTask.getExecutor());
        task.setTaskStatus(newTask.getTaskStatus());
    }

    private Task fromDto(TaskDto taskDTO) {
        final User author = userService.getCurrentUser();

        return Task.builder()
                .name(taskDTO.getName())
                .description(taskDTO.getDescription())
                .author(author)
                .executor(userRepository.findById(taskDTO.getExecutorId()).get())
                .taskStatus(taskStatusRepository.findById(taskDTO.getTaskStatusId()).get())
                .build();
    }
}
