package hexlet.code.app.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.app.config.security.JwtTokenUtils;
import hexlet.code.app.dto.TaskStatusDto;
import hexlet.code.app.dto.UserRegistrationDto;
import hexlet.code.app.model.User;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static hexlet.code.app.controllers.TaskStatusController.STATUSES_PATH;
import static hexlet.code.app.controllers.UserController.USERS_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
public class TestUtils {

    public static final String TEST_USERNAME_1 = "test_1@gmail.com";
    public static final String TEST_USERNAME_2 = "test_2@gmail.com";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    public void tearDown() {
        userRepository.deleteAll();
        taskStatusRepository.deleteAll();
    }

    private final UserRegistrationDto testNewUserRegistration = new UserRegistrationDto(
            "Sam", "Testov", TEST_USERNAME_1, "1234");

    private final TaskStatusDto testNewTaskStatus = new TaskStatusDto("New Test");

    public UserRegistrationDto getTestNewUserRegistration() {
        return testNewUserRegistration;
    }

    public TaskStatusDto getTestNewTaskStatus() {
        return testNewTaskStatus;
    }

    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    public ResultActions regDefaultUser() throws Exception {
        return regUser(testNewUserRegistration);
    }

    public ResultActions createDefaultTaskStatus() throws Exception {
        return createTaskStatus(testNewTaskStatus);
    }

    public ResultActions regUser(final UserRegistrationDto dto) throws Exception {
        final var request = post(USERS_PATH)
                .content(asJson(dto))
                .contentType(APPLICATION_JSON);
        return perform(request);
    }

    public ResultActions createTaskStatus(final TaskStatusDto taskStatusDto) throws Exception {
        final var request = post(STATUSES_PATH)
                .content(asJson(taskStatusDto))
                .contentType(APPLICATION_JSON);
        return perform(request, getUserByEmail(TEST_USERNAME_1));
    }

    public ResultActions perform(final MockHttpServletRequestBuilder request, final User user) throws Exception {
        final String token = jwtTokenUtils.generateAccessToken(user);
        request.header(AUTHORIZATION, "Bearer " + token);
        return perform(request);
    }

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
