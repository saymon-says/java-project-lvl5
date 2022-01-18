package hexlet.code.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.config.SpringConfigForIT;
import hexlet.code.dto.LabelDto;
import hexlet.code.model.Label;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static hexlet.code.config.SpringConfigForIT.TEST_PROFILE;
import static hexlet.code.controllers.LabelController.LABELS_PATH;
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
public class LabelControllerIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LabelRepository labelRepository;

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

    private LabelDto createNewTestLabel() {
        return new LabelDto("testing");
    }

    @Test
    public void createLabel() throws Exception {
        final var label = createNewTestLabel();
        final var request = post(LABELS_PATH)
                .content(asJson(label))
                .contentType(MediaType.APPLICATION_JSON);
        utils.perform(request, findUser).andExpect(status().isCreated());
        assertEquals(1, labelRepository.count());
    }

    @Test
    public void createNotAuthorizeLabel() throws Exception {
        final var label = createNewTestLabel();
        final var request = post(LABELS_PATH)
                .content(asJson(label))
                .contentType(APPLICATION_JSON);
        utils.perform(request).andExpect(status().is4xxClientError());
        assertEquals(0, labelRepository.count());
    }

    @Test
    public void getAllLabels() throws Exception {
        utils.createDefaultLabel();
        final var label = createNewTestLabel();
        final var request = post(LABELS_PATH)
                .content(asJson(label))
                .contentType(APPLICATION_JSON);
        utils.perform(request, findUser).andExpect(status().isCreated());

        final var response = utils.perform(get(LABELS_PATH), findUser)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final List<Label> labels = fromJson(response.getContentAsString(), new TypeReference<>() {
        });

        assertThat(labels).hasSize(2);
    }

    @Test
    public void getLabelById() throws Exception {
        utils.createDefaultLabel();
        final Label expectedLabel = labelRepository.findAll().get(0);
        final var response = utils.perform(
                        get(LABELS_PATH + UserController.ID_PATH, expectedLabel.getId()), findUser)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final Label label = fromJson(response.getContentAsString(), new TypeReference<>() {
        });
        assertEquals(expectedLabel.getName(), label.getName());
    }

    @Test
    public void updateLabel() throws Exception {
        utils.createDefaultLabel();
        final Long findLabelId = labelRepository.findAll().get(0).getId();

        final var updateRequest = MockMvcRequestBuilders.put(LABELS_PATH + UserController.ID_PATH, findLabelId)
                .content(asJson(createNewTestLabel()))
                .contentType(APPLICATION_JSON);
        utils.perform(updateRequest, findUser).andExpect(status().isOk());

        assertTrue(labelRepository.existsById(findLabelId));
        assertNull(labelRepository.findByName(utils.getTestNewLabel().getName()));
        assertNotNull(labelRepository.findByName(createNewTestLabel().getName()));
    }

    @Test
    public void deleteLabel() throws Exception {
        utils.createDefaultLabel();

        final long findLabelId = labelRepository.findAll().get(0).getId();

        utils.perform(MockMvcRequestBuilders.delete(LABELS_PATH + UserController.ID_PATH, findLabelId), findUser)
                .andExpect(status().isOk());

        assertEquals(0, labelRepository.count());
    }
}
