package hexlet.code.app.service;

import hexlet.code.app.dto.TaskDto;
import hexlet.code.app.model.Task;

public interface TaskService {
    Task create(TaskDto taskDto);

    Task update(Long id, TaskDto taskDto);
}
