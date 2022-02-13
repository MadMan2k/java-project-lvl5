package hexlet.code.service.impl;

import hexlet.code.dto.LabelDto;
import hexlet.code.entity.Label;
import hexlet.code.repository.LabelRepository;
import hexlet.code.service.LabelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;

    /**
     * @param dto
     * @return
     */
    @Override
    public Label createNewLabel(LabelDto dto) {
        final Label newLabel = fromDto(dto);

        return labelRepository.save(newLabel);
    }

    /**
     * @param id
     * @param dto
     * @return
     */
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
