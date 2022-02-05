package hexlet.code.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusDto {

    private static final int NAME_MIN_LENGTH = 1;

    @NotNull
    @NotBlank
    @Size(min = NAME_MIN_LENGTH)
    private String name;
}
