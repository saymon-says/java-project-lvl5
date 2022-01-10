package hexlet.code.app.controllers;

import hexlet.code.app.dto.LabelDto;
import hexlet.code.app.model.Label;
import hexlet.code.app.repository.LabelRepository;
import hexlet.code.app.service.LabelServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

@RestController
@AllArgsConstructor
@RequestMapping("/${base-url}" + "/labels")
public class LabelController {

    private LabelServiceImpl labelService;

    private LabelRepository labelRepository;

    @Operation(summary = "Get list of all labels")
    @ApiResponse(responseCode = "200", description = "List of all labels")
    @GetMapping
    public Iterable<Label> getLabels() {
        return labelRepository.findAll();
    }

    @Operation(summary = "Get the label by id")
    @ApiResponse(responseCode = "200", description = "Label by id")
    @GetMapping(ID_PATH)
    public Label getLabel(@PathVariable long id) {
        return labelRepository.findById(id).get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createLabel(@RequestBody @Valid LabelDto labelDto) {
        labelService.create(labelDto);
    }

    @PutMapping(ID_PATH)
    public void updateLabel(@PathVariable long id, @RequestBody @Valid LabelDto labelDto) {
        labelService.update(id, labelDto);
    }

    @DeleteMapping(ID_PATH)
    public void deleteLabel(@PathVariable long id) {
        labelRepository.deleteById(id);
    }

}