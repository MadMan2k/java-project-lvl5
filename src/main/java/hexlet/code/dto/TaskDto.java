package hexlet.code.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private static final int NAME_MIN_LENGTH = 1;

    @NotBlank
    @Size(min = NAME_MIN_LENGTH)
    private String name;

    private String description;

    @NotNull
    private long taskStatusId;

    private long executorId;

    private List<Long> labels;
}
