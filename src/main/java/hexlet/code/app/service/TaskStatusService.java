package hexlet.code.app.service;

import hexlet.code.app.dto.TaskStatusCreatedDto;
import hexlet.code.app.model.TaskStatus;

public interface TaskStatusService {

    TaskStatus create(TaskStatusCreatedDto taskStatusCreatedDto);

    TaskStatus update(Long id, TaskStatusCreatedDto taskStatusCreatedDto);

}
