package hexlet.code.app.service;

import hexlet.code.app.dto.TaskDto;
import hexlet.code.app.entity.Task;

public interface TaskService {
    Task createNewTask(TaskDto taskDTO);

    Task updateTask(long id, TaskDto taskDTO);
}
