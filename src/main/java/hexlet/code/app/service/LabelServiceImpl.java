package hexlet.code.app.service;

import hexlet.code.app.dto.LabelDto;
import hexlet.code.app.entity.Label;
import hexlet.code.app.repository.LabelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class LabelServiceImpl implements LabelService{
    private final LabelRepository labelRepository;

    @Override
    public Label createNewLabel(LabelDto dto) {
        final Label newLabel = fromDto(dto);

        return labelRepository.save(newLabel);
    }

    @Override
    public Label updateLabel(long id, LabelDto dto) {
        final Label label = labelRepository.findById(id).get();
        merge(label, dto);

        return labelRepository.save(label);
    }

    private void merge(Label label, LabelDto dto) {
        final Label newLabel = fromDto(dto);

        label.setName(newLabel.getName());
    }

    private Label fromDto(LabelDto dto) {
        return Label.builder()
                .name(dto.getName())
                .build();
    }
}
