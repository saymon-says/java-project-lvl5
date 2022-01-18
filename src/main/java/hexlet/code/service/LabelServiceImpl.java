package hexlet.code.service;

import hexlet.code.dto.LabelDto;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class LabelServiceImpl implements LabelService {

    private LabelRepository labelRepository;

    @Override
    public Label create(LabelDto labelDto) {
        Label newLabel = new Label();
        newLabel.setName(labelDto.getName());
        return labelRepository.save(newLabel);
    }

    @Override
    public Label update(Long id, LabelDto labelDto) {
        Label findLabel = labelRepository.getById(id);
        findLabel.setName(labelDto.getName());
        return labelRepository.save(findLabel);
    }
}
