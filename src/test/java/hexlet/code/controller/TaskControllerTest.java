package hexlet.code.controller;

import hexlet.code.config.SpringConfigForIT;
import hexlet.code.dto.LabelDto;
import hexlet.code.dto.TaskDto;
import hexlet.code.dto.TaskStatusDto;
import hexlet.code.dto.UserDto;
import hexlet.code.entity.Label;
import hexlet.code.entity.TaskStatus;
import hexlet.code.entity.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
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

import java.util.Arrays;

import static hexlet.code.config.SpringConfigForIT.TEST_PROFILE;
import static hexlet.code.controller.TaskController.TASK_CONTROLLER_PATH;
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
public class TaskControllerTest {

    private static final TaskStatusDto TASK_STATUS_DTO = new TaskStatusDto("test TaskStatus");
    private static final String NAME = "test name";
    private static final String DESCRIPTION = "test description";
    private static final LabelDto LABEL_DTO = new LabelDto("test label");
    private final UserDto testUser = new UserDto(
            "test@email.com",
            "fname",
            "lname",
            "pwd"
    );

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

        final long statusId = createNewStatus().getId();
        final long userId = userRepository.findByEmail(TEST_USERNAME).get().getId();
        final long labelId = createNewLabel().getId();

        TaskDto taskDto = new TaskDto(NAME, DESCRIPTION, statusId, userId, Arrays.asList(labelId));

        final var request = post(TASK_CONTROLLER_PATH)
                .content(asJson(taskDto))
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
    public void  createTask() throws Exception {
        final long statusId = createNewStatus().getId();
        final long userId = userRepository.findByEmail(TEST_USERNAME).get().getId();
        final long labelId = createNewLabel().getId();


        TaskDto taskDto = new TaskDto(NAME, DESCRIPTION, statusId, userId, Arrays.asList(labelId));

        final var request = post(TASK_CONTROLLER_PATH)
                .content(asJson(taskDto))
                .contentType(APPLICATION_JSON);
        utils.perform(request, TEST_USERNAME).andExpect(status().isCreated());
    }

    @Test
    public void getTaskById() throws Exception {
        long taskId = getTaskId();

        final var request = get(TASK_CONTROLLER_PATH + "/" + taskId);
        utils.perform(request, TEST_USERNAME)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(taskId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test name"));

    }

    @Test
    public void getAllTasks() throws Exception {
        final var request = get(TASK_CONTROLLER_PATH);
        utils.perform(request, TEST_USERNAME)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty());
    }

    @Test
    public void editTask() throws Exception {
        long taskId = getTaskId();

        final long statusId = createNewStatus().getId();
        final long userId = createNewUser().getId();
        final long labelId = createNewLabel().getId();

        var updatedTaskDto = new TaskDto("updated name",
                "updated description",
                statusId,
                userId,
                Arrays.asList(labelId));

        final var request = put(TASK_CONTROLLER_PATH + "/" + taskId)
                .content(asJson(updatedTaskDto))
                .contentType(APPLICATION_JSON);

        utils.perform(request, TEST_USERNAME)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("updated name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("updated description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.taskStatus.id").value(statusId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.executor.id").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.labels[0].id").value(labelId));
    }

    @Test
    public void deleteTask() throws Exception {
        long taskId = getTaskId();

        final var request = delete(TASK_CONTROLLER_PATH + "/" + taskId);

        utils.perform(request, TEST_USERNAME)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    private long getTaskId() {
        return taskRepository.findAll().get(0).getId();
    }

    private User createNewUser() {
        return userRepository.save(User.builder()
                .firstName(testUser.getFirstName())
                .lastName(testUser.getLastName())
                .email(testUser.getEmail())
                .password(testUser.getPassword())
                .build()
        );
    }

    private TaskStatus createNewStatus() {

        return taskStatusRepository.save(TaskStatus.builder()
                .name(TASK_STATUS_DTO.getName())
                .build());
    }

    private Label createNewLabel() {
        return labelRepository.save(Label.builder()
                .name(LABEL_DTO.getName())
                .build());
    }
}
