package hexlet.code.service;

import hexlet.code.dto.TaskStatusDto;
import hexlet.code.entity.TaskStatus;

public interface TaskStatusService {
    TaskStatus createNewTaskStatus(TaskStatusDto taskStatusDTO);

    TaskStatus updateTaskStatus(long id, TaskStatusDto dto);
}
