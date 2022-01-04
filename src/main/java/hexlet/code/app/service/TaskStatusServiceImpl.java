package hexlet.code.app.service;

import hexlet.code.app.dto.TaskStatusCreatedDto;
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
    public TaskStatus create(TaskStatusCreatedDto taskStatusCreatedDto) {
        TaskStatus newTask = new TaskStatus();
        newTask.setName(taskStatusCreatedDto.getName());
        return taskStatusRepository.save(newTask);
    }

    @Override
    public TaskStatus update(Long id, TaskStatusCreatedDto taskStatusCreatedDto) {
        TaskStatus findTaskStatus = taskStatusRepository.findById(id).get();
        findTaskStatus.setName(taskStatusCreatedDto.getName());
        return taskStatusRepository.save(findTaskStatus);
    }
}
