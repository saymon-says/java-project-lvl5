package hexlet.code.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.config.SpringConfigForIT;
import hexlet.code.dto.TaskDto;
import hexlet.code.model.Task;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static hexlet.code.config.SpringConfigForIT.TEST_PROFILE;
import static hexlet.code.controllers.TaskController.TASKS_PATH;
import static hexlet.code.controllers.UserController.ID_PATH;
import static hexlet.code.utils.TestUtils.TEST_USERNAME_1;
import static hexlet.code.utils.TestUtils.asJson;
import static hexlet.code.utils.TestUtils.fromJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
public class TaskControllerIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TestUtils utils;

    private User findUser;

    @BeforeEach
    public void before() throws Exception {
        utils.regDefaultUser();
        utils.createDefaultLabel();
        utils.createDefaultTaskStatus();
        findUser = userRepository.findByEmail(TEST_USERNAME_1);
    }

    @AfterEach
    public void clear() {
        utils.tearDown();
    }

    private TaskDto createNewTask() {
        return new TaskDto(
                "Test new",
                "TestTest",
                findUser.getId(),
                Collections.singletonList(labelRepository.findAll().get(0).getId()),
                taskStatusRepository.findAll().get(0).getId());
    }

    @Test
    public void createTask() throws Exception {
        final var task = createNewTask();
        final var request = post(TASKS_PATH)
                .content(asJson(task))
                .contentType(MediaType.APPLICATION_JSON);
        utils.perform(request, findUser).andExpect(status().isCreated());
        assertEquals(1, taskRepository.count());
    }

    @Test
    public void getTaskById() throws Exception {
        final var newTask = createNewTask();
        final var request = post(TASKS_PATH)
                .content(asJson(newTask))
                .contentType(MediaType.APPLICATION_JSON);
        utils.perform(request, findUser).andExpect(status().isCreated());

        assertEquals(1, taskRepository.count());

        final Task expectedTask = taskRepository.findAll().get(0);

        final var response = utils.perform(
                        get(TASKS_PATH + ID_PATH, expectedTask.getId()), findUser)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final Task task = fromJson(response.getContentAsString(), new TypeReference<>() {
        });
        assertEquals(expectedTask.getName(), task.getName());
        assertEquals(expectedTask.getDescription(), task.getDescription());
        assertEquals(expectedTask.getAuthor().getEmail(), task.getAuthor().getEmail());
    }

    @Test
    public void getAllTasks() throws Exception {
        utils.createDefaultTask();
        final var task = createNewTask();
        final var request = post(TASKS_PATH)
                .content(asJson(task))
                .contentType(APPLICATION_JSON);
        utils.perform(request, findUser).andExpect(status().isCreated());

        final var response = utils.perform(get(TASKS_PATH), findUser)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final List<Task> tasks = fromJson(response.getContentAsString(), new TypeReference<>() {
        });

        assertThat(tasks).hasSize(2);
    }

    @Test
    public void updateTask() throws Exception {
        final var task = createNewTask();
        final var request = post(TASKS_PATH)
                .content(asJson(task))
                .contentType(MediaType.APPLICATION_JSON);
        utils.perform(request, findUser).andExpect(status().isCreated());

        final Long findTaskId = taskRepository.findAll().get(0).getId();

        final var updateRequest = put(TASKS_PATH + ID_PATH, findTaskId)
                .content(asJson(createNewTask()))
                .contentType(APPLICATION_JSON);
        utils.perform(updateRequest, findUser).andExpect(status().isOk());

        assertTrue(taskRepository.existsById(findTaskId));
        assertNull(taskRepository.findByName(utils.getTestNewTask().getName()));
        assertNotNull(taskRepository.findByName(createNewTask().getName()));
    }

    @Test
    public void deleteTask() throws Exception {
        final var task = createNewTask();
        final var request = post(TASKS_PATH)
                .content(asJson(task))
                .contentType(MediaType.APPLICATION_JSON);
        utils.perform(request, findUser).andExpect(status().isCreated());

        final long findTaskId = taskRepository.findAll().get(0).getId();

        utils.perform(delete(TASKS_PATH + ID_PATH, findTaskId), findUser)
                .andExpect(status().isOk());

        assertEquals(0, taskRepository.count());
    }
}
