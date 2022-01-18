package hexlet.code.service;

import hexlet.code.dto.TaskStatusDto;
import hexlet.code.model.TaskStatus;

public interface TaskStatusService {

    TaskStatus create(TaskStatusDto taskStatusDto);

    TaskStatus update(Long id, TaskStatusDto taskStatusDto);

}
