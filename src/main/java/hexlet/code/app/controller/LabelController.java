package hexlet.code.app.controller;

import hexlet.code.app.dto.LabelDto;
import hexlet.code.app.entity.Label;
import hexlet.code.app.repository.LabelRepository;
import hexlet.code.app.service.LabelService;
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

import static hexlet.code.app.controller.LabelController.LABEL_CONTROLLER_PATH;
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
     * @return List of labels
     */
//    @Operation(summary = "Get All posts")
    @GetMapping
    public List<Label> getAll() {
        return labelRepository.findAll();
    }

    /**
     * @param id
     * @return label
     */
//    @Operation(summary = "Get post by Id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "post found"),
//            @ApiResponse(responseCode = "404", description = "post with that id not found")
//    })
    @GetMapping(ID)
    public Label getById(@PathVariable final Long id) {
        return labelRepository.findById(id).get();
    }

    /**
     * @param dto
     * @return label
     */
//    @Operation(summary = "Create new post")
//    @ApiResponse(responseCode = "201", description = "post created")
    @PostMapping
    @ResponseStatus(CREATED)
    public Label createNewLabel(@RequestBody @Valid final LabelDto dto) {
        return labelService.createNewLabel(dto);
    }

    /**
     * @param id
     * @param dto
     * @return label
     */
//    @Operation(summary = "Update post")
//    @ApiResponse(responseCode = "200", description = "post updated")
    @PutMapping(ID)
    public Label updateLabel(@PathVariable final Long id,
//                           // Schema используется, чтобы указать тип данных для параметра
//                           @Parameter(schema = @Schema(implementation = PostDto.class))
                           @RequestBody @Valid  final LabelDto dto) {
        return labelService.updateLabel(id, dto);
    }

    /**
     * @param id
     */
//    @Operation(summary = "Delete post")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "post deleted"),
//            @ApiResponse(responseCode = "404", description = "post with that id not found")
//    })
    @DeleteMapping(ID)
    public void deleteLabel(@PathVariable final Long id) {
        labelRepository.deleteById(id);
    }
}
