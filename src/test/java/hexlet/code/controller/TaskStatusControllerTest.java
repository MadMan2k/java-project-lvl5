package hexlet.code.controller;

import hexlet.code.config.SpringConfigForIT;
import hexlet.code.dto.TaskStatusDto;
import hexlet.code.repository.TaskStatusRepository;
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
import static hexlet.code.controller.TaskStatusController.TASK_STATUS_CONTROLLER_PATH;
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
public class TaskStatusControllerTest {

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TestUtils utils;

    /**
     * @throws Exception
     */
    @BeforeEach
    public void before() throws Exception {
        utils.regDefaultUser();

        var statusDto = buildTaskStatusDto();
        final var request = post(TASK_STATUS_CONTROLLER_PATH)
                .content(asJson(statusDto))
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
    public void  createTaskStatus() throws Exception {
        final var taskStatusDto = buildTaskStatusDto();
        final var request = post(TASK_STATUS_CONTROLLER_PATH)
                .content(asJson(taskStatusDto))
                .contentType(APPLICATION_JSON);
        utils.perform(request, TEST_USERNAME).andExpect(status().isCreated());
    }

    @Test
    public void getTaskStatusById() throws Exception {
        long statusId = getTaskStatusId();

        final var request = get(TASK_STATUS_CONTROLLER_PATH + "/" + statusId);
        utils.perform(request, TEST_USERNAME)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(statusId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("testStatus"));

    }

    @Test
    public void getAllTaskStatuses() throws Exception {
        final var request = get(TASK_STATUS_CONTROLLER_PATH);
        utils.perform(request, TEST_USERNAME)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty());
    }

    @Test
    public void editLabel() throws Exception {
        long statusId = getTaskStatusId();
        var updatedTaskStatusDto = new TaskStatusDto("updatedStatus");

        final var request = put(TASK_STATUS_CONTROLLER_PATH + "/" + statusId)
                .content(asJson(updatedTaskStatusDto))
                .contentType(APPLICATION_JSON);

        utils.perform(request, TEST_USERNAME)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("updatedStatus"));
    }

    @Test
    public void deleteTaskStatus() throws Exception {
        long statusId = getTaskStatusId();

        final var request = delete(TASK_STATUS_CONTROLLER_PATH + "/" + statusId);

        utils.perform(request, TEST_USERNAME)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    private long getTaskStatusId() {
        return taskStatusRepository.findAll().get(0).getId();
    }

    private TaskStatusDto buildTaskStatusDto() {
        return new TaskStatusDto("testStatus");
    }
}
