package hexlet.code.app.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

import hexlet.code.app.dto.UserDto;
import hexlet.code.app.entity.User;
import hexlet.code.app.repository.UserRepository;
import hexlet.code.app.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static hexlet.code.app.controller.UserController.USER_CONTROLLER_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
public class TestUtils {

    public static final String TEST_USERNAME = "email@email.com";
    public static final String TEST_USERNAME_2 = "email2@email.com";

    private final UserDto testRegistrationDto = new UserDto(
            TEST_USERNAME,
            "fname",
            "lname",
            "pwd"
    );

    /**
     * @return UserDto
     */
    public UserDto getTestRegistrationDto() {
        return testRegistrationDto;
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PostCommentRepository postCommentRepository;

//    @Autowired
//    private PostRepository postRepository;

    @Autowired
    private TokenService tokenService;

    /**
     * remove all.
     */
    public void tearDown() {
//        postCommentRepository.deleteAll();
//        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    /**
     * @param email
     * @return User
     */
    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email).get();
    }

    /**
     * @return ResultActions
     * @throws Exception
     */
    public ResultActions regDefaultUser() throws Exception {
        return regUser(testRegistrationDto);
    }

    /**
     * @param dto
     * @return ResultActions
     * @throws Exception
     */
    public ResultActions regUser(final UserDto dto) throws Exception {
        final var request = post(USER_CONTROLLER_PATH)
                .content(asJson(dto))
                .contentType(APPLICATION_JSON);

        return perform(request);
    }

    /**
     * @param request
     * @param byUser
     * @return ResultActions
     * @throws Exception
     */
    public ResultActions perform(final MockHttpServletRequestBuilder request, final String byUser) throws Exception {
        final String token = tokenService.expiring(Map.of("username", byUser));
        request.header(AUTHORIZATION, token);

        return perform(request);
    }

    /**
     * @param request
     * @return ResultActions
     * @throws Exception
     */
    public ResultActions perform(final MockHttpServletRequestBuilder request) throws Exception {
        return mockMvc.perform(request);
    }

    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    public static String asJson(final Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

    public static <T> T fromJson(final String json, final TypeReference<T> to) throws JsonProcessingException {
        return MAPPER.readValue(json, to);
    }
}
