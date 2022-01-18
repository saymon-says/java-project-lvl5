package hexlet.code.service;

import hexlet.code.dto.TaskDto;
import hexlet.code.model.Task;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
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
        newTask.setLabels(labelRepository.findAllById(taskDto.getLabelIds()));
        return taskRepository.save(newTask);
    }
}
