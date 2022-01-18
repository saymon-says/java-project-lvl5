package hexlet.code.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.config.SpringConfigForIT;
import hexlet.code.dto.TaskStatusDto;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static hexlet.code.config.SpringConfigForIT.TEST_PROFILE;
import static hexlet.code.controllers.TaskStatusController.STATUSES_PATH;
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
public class TaskStatusControllerIT {

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestUtils utils;

    private User findUser;

    @BeforeEach
    public void before() throws Exception {
        utils.regDefaultUser();
        findUser = userRepository.findByEmail(TEST_USERNAME_1);
    }

    @AfterEach
    public void clear() {
        utils.tearDown();
    }

    private TaskStatusDto createTaskStatus() {
        return new TaskStatusDto("New Test Status");
    }

    @Test
    public void createStatus() throws Exception {
        final var taskStatus = createTaskStatus();
        final var request = post(STATUSES_PATH)
                .content(asJson(taskStatus))
                .contentType(APPLICATION_JSON);
        utils.perform(request, findUser).andExpect(status().isCreated());
        assertEquals(1, taskStatusRepository.count());
    }

    @Test
    public void createNotAuthorizeStatus() throws Exception {
        final var taskStatus = createTaskStatus();
        final var request = post(STATUSES_PATH)
                .content(asJson(taskStatus))
                .contentType(APPLICATION_JSON);
        utils.perform(request).andExpect(status().is4xxClientError());
        assertEquals(0, taskStatusRepository.count());
    }

    @Test
    public void createInvalidStatus() throws Exception {
        final var invalidTaskStatus = new TaskStatusDto("");
        final var request = post(STATUSES_PATH)
                .content(asJson(invalidTaskStatus))
                .contentType(APPLICATION_JSON);
        utils.perform(request).andExpect(status().is4xxClientError());
        assertEquals(0, taskStatusRepository.count());
    }

    @Test
    public void deleteStatus() throws Exception {
        utils.createDefaultTaskStatus();

        final Long taskStatusId = taskStatusRepository.findAll().get(0).getId();

        utils.perform(delete(STATUSES_PATH + ID_PATH, taskStatusId), findUser)
                .andExpect(status().isOk());

        assertEquals(0, taskStatusRepository.count());

    }

    @Test
    public void getTaskStatusById() throws Exception {
        utils.createDefaultTaskStatus();

        final TaskStatus expectedTaskStatus = taskStatusRepository.findAll().get(0);
        final var response = utils.perform(
                        get(STATUSES_PATH + ID_PATH, expectedTaskStatus.getId()), findUser)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final TaskStatus taskStatus = fromJson(response.getContentAsString(), new TypeReference<>() {
        });

        assertEquals(expectedTaskStatus.getName(), taskStatus.getName());
    }

    @Test
    public void getAllTaskStatuses() throws Exception {
        utils.createDefaultTaskStatus();

        final var taskStatus = createTaskStatus();
        final var request = post(STATUSES_PATH)
                .content(asJson(taskStatus))
                .contentType(APPLICATION_JSON);
        utils.perform(request, findUser).andExpect(status().isCreated());

        final var response = utils.perform(get(STATUSES_PATH), findUser)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final List<TaskStatus> statuses = fromJson(response.getContentAsString(), new TypeReference<>() {
        });

        assertThat(statuses).hasSize(2);
    }

    @Test
    public void updateTaskStatus() throws Exception {
        utils.createDefaultTaskStatus();
        final Long findTaskStatusId = taskStatusRepository.findAll().get(0).getId();
        final var updateRequest = put(STATUSES_PATH + ID_PATH, findTaskStatusId)
                .content(asJson(createTaskStatus()))
                .contentType(APPLICATION_JSON);

        utils.perform(updateRequest, findUser).andExpect(status().isOk());

        assertTrue(taskStatusRepository.existsById(findTaskStatusId));
        assertNull(taskStatusRepository.findByName(utils.getTestNewTaskStatus().getName()));
        assertNotNull(taskStatusRepository.findByName("New Test Status"));
    }

}
