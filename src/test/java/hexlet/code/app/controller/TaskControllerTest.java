package hexlet.code.app.controller;

import hexlet.code.app.config.SpringConfigForIT;
import hexlet.code.app.dto.LabelDto;
import hexlet.code.app.dto.TaskDto;
import hexlet.code.app.dto.TaskStatusDto;
import hexlet.code.app.entity.Label;
import hexlet.code.app.entity.TaskStatus;
import hexlet.code.app.repository.LabelRepository;
import hexlet.code.app.repository.TaskRepository;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.repository.UserRepository;
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

import java.util.Arrays;

import static hexlet.code.app.config.SpringConfigForIT.TEST_PROFILE;
import static hexlet.code.app.controller.TaskController.TASK_CONTROLLER_PATH;
import static hexlet.code.app.utils.TestUtils.TEST_USERNAME;
import static hexlet.code.app.utils.TestUtils.asJson;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles(TEST_PROFILE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringConfigForIT.class)
public class TaskControllerTest {

    private static final TaskStatusDto TASK_STATUS_DTO = new TaskStatusDto("test TaskStatus");
    private static final String NAME = "test name";
    private static final String DESCRIPTION = "test description";
    private static final LabelDto LABEL_DTO = new LabelDto("test label");

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private UserRepository userRepository;

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
    }

    /**
     * clear all changes.
     */
    @AfterEach
    public void clear() {
        utils.tearDown();
    }

    @Test
    public void  createTask() throws Exception {
        final long statusId = createNewStatus().getId();
        final long userId = userRepository.findByEmail(TEST_USERNAME).get().getId();
        final long labelId = createNewLable().getId();


        TaskDto taskDto = new TaskDto(NAME, DESCRIPTION, statusId, userId, Arrays.asList(labelId));

        final var request = post(TASK_CONTROLLER_PATH)
                .content(asJson(taskDto))
                .contentType(APPLICATION_JSON);
        utils.perform(request, TEST_USERNAME).andExpect(status().isCreated());
    }

    private TaskStatus createNewStatus() {

        return taskStatusRepository.save(TaskStatus.builder()
                .name(TASK_STATUS_DTO.getName())
                .build());
    }

    private Label createNewLable() {
        return labelRepository.save(Label.builder()
                .name(LABEL_DTO.getName())
                .build());
    }
}
