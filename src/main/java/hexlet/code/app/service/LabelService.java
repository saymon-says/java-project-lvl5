package hexlet.code.app.service;

import hexlet.code.app.dto.LabelDto;
import hexlet.code.app.model.Label;

public interface LabelService {

    Label create(LabelDto labelDto);

    Label update(Long id, LabelDto labelDto);

}
