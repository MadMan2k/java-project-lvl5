package hexlet.code.service;

import hexlet.code.dto.TaskDto;
import hexlet.code.entity.Task;

public interface TaskService {
    Task createNewTask(TaskDto taskDTO);

    Task updateTask(long id, TaskDto taskDTO);
}
