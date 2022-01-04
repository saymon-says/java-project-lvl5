package hexlet.code.app.controllers;

import hexlet.code.app.dto.TaskStatusCreatedDto;
import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.service.TaskStatusServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static hexlet.code.app.controllers.UserController.ID_path;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + "/statuses")
public class TaskStatusController {

    private TaskStatusRepository taskStatusRepository;

    private TaskStatusServiceImpl taskStatusService;

    @GetMapping(ID_path)
    public TaskStatus getTaskStatus(@PathVariable long id) {
        return taskStatusRepository.findById(id).get();
    }

    @GetMapping
    public Iterable<TaskStatus> getAllTaskStatuses() {
        return taskStatusRepository.findAll();
    }

    @PostMapping
    public void createTaskStatus(@RequestBody @Valid TaskStatusCreatedDto taskStatusCreatedDto) {
        taskStatusService.create(taskStatusCreatedDto);
    }

    @PutMapping(ID_path)
    public void updateTaskStatus(@PathVariable long id, @RequestBody @Valid TaskStatusCreatedDto taskStatusCreatedDto) {
        taskStatusService.update(id, taskStatusCreatedDto);
    }

    @DeleteMapping(ID_path)
    public void deleteTaskStatus(@PathVariable long id) {
        taskStatusRepository.deleteById(id);
    }
}
