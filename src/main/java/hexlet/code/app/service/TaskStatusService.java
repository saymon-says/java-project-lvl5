package hexlet.code.app.service;

import hexlet.code.app.dto.TaskCreatedDto;
import hexlet.code.app.model.TaskStatus;

public interface TaskStatusService {

    TaskStatus create(TaskCreatedDto taskCreatedDto);

    TaskStatus update(Long id, TaskCreatedDto taskCreatedDto);

}
