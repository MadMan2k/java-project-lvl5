package hexlet.code.controller;

import hexlet.code.dto.LabelDto;
import hexlet.code.entity.Label;
import hexlet.code.repository.LabelRepository;
import hexlet.code.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
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
import java.util.List;

import static hexlet.code.controller.LabelController.LABEL_CONTROLLER_PATH;
import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + LABEL_CONTROLLER_PATH)
public class LabelController {

    public static final String LABEL_CONTROLLER_PATH = "/labels";
    public static final String ID = "/{id}";

    private final LabelRepository labelRepository;
    private final LabelService labelService;

    /**
     * @param dto
     * @return label
     */
    @Operation(summary = "Create new label")
    @ApiResponse(responseCode = "201", description = "Label created")
    @PostMapping
    @ResponseStatus(CREATED)
    public Label createNewLabel(@Parameter(description = "Label to save") @RequestBody @Valid final LabelDto dto) {
        return labelService.createNewLabel(dto);
    }

    /**
     * @return List of labels
     */
    @Operation(summary = "Get all labels")
    @ApiResponses(@ApiResponse(responseCode = "200", content =
        @Content(schema = @Schema(implementation = Label.class))
        ))
    @GetMapping
    public List<Label> getAll() {
        return labelRepository.findAll();
    }

    /**
     * @param id
     * @return label
     */
    @Operation(summary = "Get label by labelID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Label found"),
        @ApiResponse(responseCode = "404", description = "Label with that id not found")
    })
    @GetMapping(ID)
    public Label getById(@PathVariable final Long id) {
        return labelRepository.findById(id).get();
    }

    /**
     * @param id
     * @param dto
     * @return label
     */
    @Operation(summary = "Update existing label")
    @ApiResponse(responseCode = "200", description = "Label updated")
    @PutMapping(ID)
    public Label updateLabel(@Parameter(description = "Label for update id") @PathVariable final Long id,
                             @Parameter(schema = @Schema(implementation = LabelDto.class))
                             @RequestBody @Valid  final LabelDto dto) {
        return labelService.updateLabel(id, dto);
    }

    /**
     * @param id
     */
    @Operation(summary = "Delete label")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Label deleted"),
        @ApiResponse(responseCode = "404", description = "Label with that id not found")
    })
    @DeleteMapping(ID)
    public void deleteLabel(@Parameter(description = "Id of label to be deleted") @PathVariable final Long id) {
        labelRepository.deleteById(id);
    }
}
