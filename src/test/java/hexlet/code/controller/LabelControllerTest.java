package hexlet.code.controller;

import hexlet.code.config.SpringConfigForIT;
import hexlet.code.dto.LabelDto;
import hexlet.code.dto.TaskStatusDto;
import hexlet.code.repository.LabelRepository;
import hexlet.code.app.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static hexlet.code.config.SpringConfigForIT.TEST_PROFILE;
import static hexlet.code.controller.LabelController.LABEL_CONTROLLER_PATH;
import static hexlet.code.app.utils.TestUtils.TEST_USERNAME;
import static hexlet.code.app.utils.TestUtils.asJson;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles(TEST_PROFILE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringConfigForIT.class)
public class LabelControllerTest {

    private static final TaskStatusDto TASK_STATUS_DTO = new TaskStatusDto("test TaskStatus");
    private static final String NAME = "test name";
    private static final String DESCRIPTION = "test description";

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private TestUtils utils;

    /**
     * @throws Exception
     */
    @BeforeEach
    public void before() throws Exception {
        utils.regDefaultUser();

        var labelDto = buildLabelDto();
        final var request = post(LABEL_CONTROLLER_PATH)
                .content(asJson(labelDto))
                .contentType(APPLICATION_JSON);
        utils.perform(request, TEST_USERNAME);
    }

    /**
     * clear all changes.
     */
    @AfterEach
    public void clear() {
        utils.tearDown();
    }

    @Test
    public void  createLabel() throws Exception {
        var labelDto = buildLabelDto();
        final var request = post(LABEL_CONTROLLER_PATH)
                .content(asJson(labelDto))
                .contentType(APPLICATION_JSON);
        utils.perform(request, TEST_USERNAME).andExpect(status().isCreated());
    }

    @Test
    public void getLabelById() throws Exception {
        long labelId = getLabelId();

        final var request = get(LABEL_CONTROLLER_PATH + "/" + labelId);
        utils.perform(request, TEST_USERNAME)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(labelId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("testLabel"));

    }

    @Test
    public void getAllLabels() throws Exception {
        final var request = get(LABEL_CONTROLLER_PATH);
        utils.perform(request, TEST_USERNAME)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty());
    }

    @Test
    public void editLabel() throws Exception {
        long labelId = getLabelId();
        var updatedLabelDto = new LabelDto("updatedLabel");

        final var request = put(LABEL_CONTROLLER_PATH + "/" + labelId)
                .content(asJson(updatedLabelDto))
                .contentType(APPLICATION_JSON);

        utils.perform(request, TEST_USERNAME)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("updatedLabel"));
    }

    @Test
    public void deleteLabel() throws Exception {
        long labelId = getLabelId();

        final var request = delete(LABEL_CONTROLLER_PATH + "/" + labelId);

        utils.perform(request, TEST_USERNAME)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    private long getLabelId() {
        return labelRepository.findAll().get(0).getId();
    }

    private LabelDto buildLabelDto() {
        return new LabelDto("testLabel");
    }
}
