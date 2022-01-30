package hexlet.code.app.service;

import hexlet.code.app.dto.TaskStatusDto;
import hexlet.code.app.entity.TaskStatus;

public interface TaskStatusService {
    TaskStatus createNewTaskStatus(TaskStatusDto taskStatusDTO);

    TaskStatus updateTaskStatus(long id, TaskStatusDto dto);
}
