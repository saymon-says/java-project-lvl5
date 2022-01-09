package hexlet.code.app.service;

import hexlet.code.app.dto.TaskStatusDto;
import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.repository.TaskStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class TaskStatusServiceImpl implements TaskStatusService {

    private TaskStatusRepository taskStatusRepository;
    @Override
    public TaskStatus create(TaskStatusDto taskStatusDto) {
        TaskStatus newTask = new TaskStatus();
        newTask.setName(taskStatusDto.getName());
        return taskStatusRepository.save(newTask);
    }

    @Override
    public TaskStatus update(Long id, TaskStatusDto taskStatusDto) {
        TaskStatus findTaskStatus = taskStatusRepository.findById(id).get();
        findTaskStatus.setName(taskStatusDto.getName());
        return taskStatusRepository.save(findTaskStatus);
    }
}
