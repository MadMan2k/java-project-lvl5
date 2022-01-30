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
public class TaskStatusServiceImpl implements TaskStatusService {

    private final TaskStatusRepository taskStatusRepository;

    /**
     * @param taskStatusDTO
     * @return
     */
    @Override
    public TaskStatus createNewTaskStatus(TaskStatusDTO taskStatusDTO) {
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
    public TaskStatus updateTaskStatus(long id, TaskStatusDTO dto) {

        final TaskStatus taskStatusToUpdate = taskStatusRepository.findById(id).get();
        taskStatusToUpdate.setName(dto.getName());

        return taskStatusRepository.save(taskStatusToUpdate);

    }
}
