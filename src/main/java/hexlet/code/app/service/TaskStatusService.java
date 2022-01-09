package hexlet.code.app.service;

import hexlet.code.app.dto.TaskStatusDto;
import hexlet.code.app.model.TaskStatus;

public interface TaskStatusService {

    TaskStatus create(TaskStatusDto taskStatusDto);

    TaskStatus update(Long id, TaskStatusDto taskStatusDto);

}
