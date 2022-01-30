package hexlet.code.app.service;

import hexlet.code.app.dto.TaskStatusDTO;
import hexlet.code.app.entity.TaskStatus;

public interface TaskStatusService {
    TaskStatus createNewTaskStatus(TaskStatusDTO taskStatusDTO);

    TaskStatus updateTaskStatus(long id, TaskStatusDTO dto);
}
