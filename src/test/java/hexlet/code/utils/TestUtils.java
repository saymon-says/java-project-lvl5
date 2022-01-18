package hexlet.code.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.config.security.JwtTokenUtils;
import hexlet.code.controllers.LabelController;
import hexlet.code.controllers.TaskController;
import hexlet.code.controllers.TaskStatusController;
import hexlet.code.controllers.UserController;
import hexlet.code.dto.LabelDto;
import hexlet.code.dto.TaskDto;
import hexlet.code.dto.TaskStatusDto;
import hexlet.code.dto.UserRegistrationDto;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collections;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
public class TestUtils {

    public static final String TEST_USERNAME_1 = "test_1@gmail.com";
    public static final String TEST_USERNAME_2 = "test_2@gmail.com";
    public static final String DEFAULT_LABEL = "Created label";
    public static final String DEFAULT_TASK_STATUS = "Created status";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    public void tearDown() {
        taskRepository.deleteAll();
        taskStatusRepository.deleteAll();
        labelRepository.deleteAll();
        userRepository.deleteAll();
    }

    private final UserRegistrationDto testNewUserRegistration = new UserRegistrationDto(
            "Sam",
            "Testov",
            TEST_USERNAME_1,
            "1234");

    private final TaskStatusDto testNewTaskStatus = new TaskStatusDto(DEFAULT_TASK_STATUS);

    private final LabelDto testNewLabel = new LabelDto(DEFAULT_LABEL);

    private final TaskDto testNewTask = new TaskDto(
            "Test",
            "TestTest",
            1,
            Collections.singletonList(1L),
            1
    );

    public UserRegistrationDto getTestNewUserRegistration() {
        return testNewUserRegistration;
    }

    public TaskStatusDto getTestNewTaskStatus() {
        return testNewTaskStatus;
    }

    public LabelDto getTestNewLabel() {
        return testNewLabel;
    }

    public TaskDto getTestNewTask() {
        return testNewTask;
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

    public ResultActions createDefaultLabel() throws Exception {
        return createLabel(testNewLabel);
    }

    public ResultActions createDefaultTask() throws Exception {
        return createTask(testNewTask);
    }

    public ResultActions regUser(final UserRegistrationDto dto) throws Exception {
        final var request = post(UserController.USERS_PATH)
                .content(asJson(dto))
                .contentType(APPLICATION_JSON);
        return perform(request);
    }

    public ResultActions createTaskStatus(final TaskStatusDto taskStatusDto) throws Exception {
        final var request = post(TaskStatusController.STATUSES_PATH)
                .content(asJson(taskStatusDto))
                .contentType(APPLICATION_JSON);
        return perform(request, getUserByEmail(TEST_USERNAME_1));
    }

    public ResultActions createLabel(final LabelDto labelDto) throws Exception {
        final var request = post(LabelController.LABELS_PATH)
                .content(asJson(labelDto))
                .contentType(APPLICATION_JSON);
        return perform(request, getUserByEmail(TEST_USERNAME_1));
    }

    public ResultActions createTask(final TaskDto taskDto) throws Exception {
        final var request = post(TaskController.TASKS_PATH)
                .content(asJson(taskDto))
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
