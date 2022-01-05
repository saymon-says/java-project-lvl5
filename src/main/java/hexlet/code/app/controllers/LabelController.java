package hexlet.code.app.controllers;

import hexlet.code.app.dto.LabelDto;
import hexlet.code.app.model.Label;
import hexlet.code.app.repository.LabelRepository;
import hexlet.code.app.service.LabelServiceImpl;
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

@RestController
@AllArgsConstructor
@RequestMapping("/${base-url}" + "/labels")
public class LabelController {

    private LabelServiceImpl labelService;

    private LabelRepository labelRepository;

    @GetMapping
    public Iterable<Label> getLabels() {
        return labelRepository.findAll();
    }

    @GetMapping(ID_path)
    public Label getLabel(@PathVariable long id) {
        return labelRepository.findById(id).get();
    }

    @PostMapping
    public void createLabel(@RequestBody @Valid LabelDto labelDto) {
        labelService.create(labelDto);
    }

    @PutMapping(ID_path)
    public void updateLabel(@PathVariable long id, @RequestBody @Valid LabelDto labelDto) {
        labelService.update(id, labelDto);
    }

    @DeleteMapping(ID_path)
    public void deleteLabel(@PathVariable long id) {
        labelRepository.deleteById(id);
    }

}
