package hexlet.code.app.controllers;

import hexlet.code.app.dto.LabelDto;
import hexlet.code.app.model.Label;
import hexlet.code.app.repository.LabelRepository;
import hexlet.code.app.service.LabelServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import static hexlet.code.app.controllers.LabelController.LABELS_PATH;
import static hexlet.code.app.controllers.UserController.ID_PATH;

@RestController
@AllArgsConstructor
@RequestMapping("/${base-url}" + LABELS_PATH)
public class LabelController {

    private LabelServiceImpl labelService;

    private LabelRepository labelRepository;

    public static final String LABELS_PATH = "/labels";

    @Operation(summary = "Get list of all labels")
    @ApiResponse(responseCode = "200", description = "List of all labels")
    @GetMapping
    public Iterable<Label> getLabels() {
        return this.labelRepository.findAll();
    }

    @Operation(summary = "Get label by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Label found"),
            @ApiResponse(responseCode = "404", description = "Label with that id not found")
    })
    @GetMapping(ID_PATH)
    public Label getLabel(@Parameter(description = "Id of label to be found")
                          @PathVariable final long id) {
        return this.labelRepository.findById(id).get();
    }

    @Operation(summary = "Create new label")
    @ApiResponse(responseCode = "201", description = "Label created")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createLabel(@RequestBody @Valid LabelDto labelDto) {
        this.labelService.create(labelDto);
    }

    @Operation(summary = "Update label by his id")
    @ApiResponse(responseCode = "200", description = "Label updated")
    @PutMapping(ID_PATH)
    public void updateLabel(@Parameter(description = "Id of label to be found")
                            @PathVariable final long id, @RequestBody @Valid LabelDto labelDto) {
        this.labelService.update(id, labelDto);
    }

    @Operation(summary = "Delete label by his id")
    @ApiResponse(responseCode = "200", description = "Label deleted")
    @DeleteMapping(ID_PATH)
    public void deleteLabel(@Parameter(description = "Id of label to be found")
                            @PathVariable final long id) {
        this.labelRepository.deleteById(id);
    }

}
