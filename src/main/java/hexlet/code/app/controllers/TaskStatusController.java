package hexlet.code.app.controllers;

import hexlet.code.app.dto.TaskStatusDto;
import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.service.TaskStatusServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static hexlet.code.app.controllers.UserController.ID_PATH;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + "/statuses")
public class TaskStatusController {

    private TaskStatusRepository taskStatusRepository;

    private TaskStatusServiceImpl taskStatusService;

    @Operation(summary = "Get task status by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status found"),
            @ApiResponse(responseCode = "404")
    })
    @GetMapping(ID_PATH)
    public ResponseEntity<TaskStatus> getTaskStatus(@Parameter(description = "Id of task status to be found")
                                    @PathVariable long id) {
        if (taskStatusRepository.existsById(id)) {
            return new ResponseEntity<>(taskStatusRepository.findById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get list of all task status")
    @ApiResponse(responseCode = "200", description = "List of all task status")
    @GetMapping
    public Iterable<TaskStatus> getAllTaskStatuses() {
        return taskStatusRepository.findAll();
    }

    @Operation(summary = "Create new task status")
    @ApiResponse(responseCode = "201", description = "Task status created")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTaskStatus(@Parameter(description = "Task status date to save")
                                 @RequestBody @Valid TaskStatusDto taskStatusDto) {
        taskStatusService.create(taskStatusDto);
    }

    @Operation(summary = "Update task status by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status updated"),
            @ApiResponse(responseCode = "404", description = "Task status with that id not found")
    })
    @PutMapping(ID_PATH)
    public ResponseEntity<String> updateTaskStatus(@Parameter(description = "Id of task status to be updated")
                                                   @PathVariable long id, @RequestBody @Valid TaskStatusDto taskStatusDto) {
        if (taskStatusRepository.existsById(id)) {
            taskStatusService.update(id, taskStatusDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Task status with that id not found", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete task status by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status deleted"),
            @ApiResponse(responseCode = "404", description = "Task status with that id not found")
    })
    @DeleteMapping(ID_PATH)
    public ResponseEntity<String> deleteTaskStatus(@Parameter(description = "Id of task status to be deleted")
                                                   @PathVariable long id) {
        if (taskStatusRepository.existsById(id)) {
            taskStatusRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Task status with that id not found", HttpStatus.NOT_FOUND);
        }
    }
}
