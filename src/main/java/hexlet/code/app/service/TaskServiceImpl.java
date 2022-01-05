package hexlet.code.app.service;

import hexlet.code.app.dto.TaskDto;
import hexlet.code.app.model.Task;
import hexlet.code.app.repository.LabelRepository;
import hexlet.code.app.repository.TaskRepository;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    private UserServiceImpl userService;

    private TaskStatusRepository taskStatusRepository;

    private UserRepository userRepository;

    private LabelRepository labelRepository;

    @Override
    public Task create(TaskDto taskDto) {
        Task newTask = new Task();
        return fillInTheTask(taskDto, newTask);
    }

    @Override
    public Task update(Long id, TaskDto taskDto) {
        Task findTask = taskRepository.findById(id).get();
        return fillInTheTask(taskDto, findTask);
    }

    private Task fillInTheTask(TaskDto taskDto, Task newTask) {
        newTask.setName(taskDto.getName());
        newTask.setTaskStatus(taskStatusRepository.findById(taskDto.getTaskStatusId()).get());
        newTask.setDescription(taskDto.getDescription());
        newTask.setExecutor(userRepository.findById(taskDto.getExecutorId()).orElse(null));
        newTask.setAuthor(userService.findByToken());
        newTask.setLabel(labelRepository.findAllById(taskDto.getLabelIds()));
        return taskRepository.save(newTask);
    }
}
