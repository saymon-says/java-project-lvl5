package hexlet.code.app.controllers;

import hexlet.code.app.dto.TaskStatusDto;
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

import static hexlet.code.app.controllers.UserController.ID_PATH;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + "/statuses")
public class TaskStatusController {

    private TaskStatusRepository taskStatusRepository;

    private TaskStatusServiceImpl taskStatusService;

    @GetMapping(ID_PATH)
    public TaskStatus getTaskStatus(@PathVariable long id) {
        return taskStatusRepository.findById(id).get();
    }

    @GetMapping
    public Iterable<TaskStatus> getAllTaskStatuses() {
        return taskStatusRepository.findAll();
    }

    @PostMapping
    public void createTaskStatus(@RequestBody @Valid TaskStatusDto taskStatusDto) {
        taskStatusService.create(taskStatusDto);
    }

    @PutMapping(ID_PATH)
    public void updateTaskStatus(@PathVariable long id, @RequestBody @Valid TaskStatusDto taskStatusDto) {
        taskStatusService.update(id, taskStatusDto);
    }

    @DeleteMapping(ID_PATH)
    public void deleteTaskStatus(@PathVariable long id) {
        taskStatusRepository.deleteById(id);
    }
}
