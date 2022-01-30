package hexlet.code.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusDTO {

    private static final int NAME_MIN_LENGTH = 1;

    @NotBlank
    @Size(min = NAME_MIN_LENGTH)
    private String name;
}
