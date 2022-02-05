package hexlet.code.service;

import hexlet.code.dto.TaskStatusDto;
import hexlet.code.entity.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class TaskStatusServiceImpl implements TaskStatusService {

    private final TaskStatusRepository taskStatusRepository;

    /**
     * @param taskStatusDTO
     * @return
     */
    @Override
    public TaskStatus createNewTaskStatus(TaskStatusDto taskStatusDTO) {
        final TaskStatus taskStatus = new TaskStatus();

        taskStatus.setName(taskStatusDTO.getName());

        return taskStatusRepository.save(taskStatus);
    }

    /**
     * @param id
     * @param dto
     * @return
     */
    @Override
    public TaskStatus updateTaskStatus(long id, TaskStatusDto dto) {

        final TaskStatus taskStatusToUpdate = taskStatusRepository.findById(id).get();
        taskStatusToUpdate.setName(dto.getName());

        return taskStatusRepository.save(taskStatusToUpdate);

    }
}
