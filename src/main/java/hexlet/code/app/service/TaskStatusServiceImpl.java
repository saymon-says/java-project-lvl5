package hexlet.code.app.service;

import hexlet.code.app.dto.TaskCreatedDto;
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
    public TaskStatus create(TaskCreatedDto taskCreatedDto) {
        TaskStatus newTask = new TaskStatus();
        newTask.setName(taskCreatedDto.getName());
        return taskStatusRepository.save(newTask);
    }

    @Override
    public TaskStatus update(Long id, TaskCreatedDto taskCreatedDto) {
        TaskStatus findTaskStatus = taskStatusRepository.findById(id).get();
        findTaskStatus.setName(taskCreatedDto.getName());
        return taskStatusRepository.save(findTaskStatus);
    }
}
