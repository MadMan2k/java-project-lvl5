package hexlet.code.app.service;

import hexlet.code.app.dto.TaskStatusDTO;
import hexlet.code.app.entity.TaskStatus;
import hexlet.code.app.repository.TaskStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class TaskStatusServiceImpl implements TaskStatusService{

    private final TaskStatusRepository taskStatusRepository;

    @Override
    public TaskStatus createNewTaskStatus(TaskStatusDTO taskStatusDTO) {
        final TaskStatus taskStatus = new TaskStatus();

        System.out.println("THIS IS TASK STATUS BEFORE SAVE : " + taskStatus);
        System.out.println("THIS IS TASK STATUS DTO NAME : " + taskStatusDTO.getName());

        taskStatus.setName(taskStatusDTO.getName());

        return taskStatusRepository.save(taskStatus);
    }
}
