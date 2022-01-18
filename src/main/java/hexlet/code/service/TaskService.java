package hexlet.code.service;

import hexlet.code.dto.TaskDto;
import hexlet.code.model.Task;

public interface TaskService {
    Task create(TaskDto taskDto);

    Task update(Long id, TaskDto taskDto);
}
