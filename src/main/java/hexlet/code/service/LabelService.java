package hexlet.code.service;

import hexlet.code.dto.LabelDto;
import hexlet.code.model.Label;

public interface LabelService {

    Label create(LabelDto labelDto);

    Label update(Long id, LabelDto labelDto);

}
