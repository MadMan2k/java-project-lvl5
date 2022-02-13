package hexlet.code.service.impl;

import hexlet.code.dto.TaskDto;
import hexlet.code.entity.Task;
import hexlet.code.entity.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.TaskService;
import hexlet.code.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final LabelRepository labelRepository;

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
        task.setLabels(newTask.getLabels());

    }

    private Task fromDto(TaskDto taskDTO) {
        final User author = userService.getCurrentUser();

        return Task.builder()
                .name(taskDTO.getName())
                .description(taskDTO.getDescription())
                .author(author)
                .executor(userRepository.findById(taskDTO.getExecutorId()).get())
                .taskStatus(taskStatusRepository.findById(taskDTO.getTaskStatusId()).get())
                .labels(labelRepository.findAllById(taskDTO.getLabelIds()).stream().collect(Collectors.toList()))
                .build();
    }
}
