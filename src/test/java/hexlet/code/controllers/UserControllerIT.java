package hexlet.code.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.config.SpringConfigForIT;
import hexlet.code.dto.UserLoginDto;
import hexlet.code.dto.UserRegistrationDto;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static hexlet.code.config.SpringConfigForIT.TEST_PROFILE;
import static hexlet.code.controllers.AuthController.LOGIN_PATH;
import static hexlet.code.controllers.UserController.ID_PATH;
import static hexlet.code.controllers.UserController.USERS_PATH;
import static hexlet.code.utils.TestUtils.TEST_USERNAME_1;
import static hexlet.code.utils.TestUtils.TEST_USERNAME_2;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles(TEST_PROFILE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringConfigForIT.class)
public class UserControllerIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestUtils utils;

    @AfterEach
    public void clear() {
        utils.tearDown();
    }

    @Test
    public void registration() throws Exception {
        assertEquals(0, userRepository.count());
        utils.regDefaultUser().andExpect(status().isCreated());
        assertEquals(1, userRepository.count());
    }

    @Test
    public void registrationInvalidUser() throws Exception {
        assertEquals(0, userRepository.count());
        UserRegistrationDto userDto = new UserRegistrationDto(
                "first",
                "",
                TEST_USERNAME_2,
                "1"
        );
        utils.regUser(userDto).andExpect(status().is4xxClientError());
        assertEquals(0, userRepository.count());
    }

    @Test
    public void getUserById() throws Exception {
        utils.regDefaultUser();
        final User expectedUser = userRepository.findAll().get(0);
        final var response = utils.perform(
                        get(USERS_PATH + ID_PATH, expectedUser.getId()), expectedUser)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final User user = fromJson(response.getContentAsString(), new TypeReference<>() {
        });

        assertEquals(expectedUser.getId(), user.getId());
        assertEquals(expectedUser.getEmail(), user.getEmail());
        assertEquals(expectedUser.getFirstName(), user.getFirstName());
        assertEquals(expectedUser.getLastName(), user.getLastName());
    }

    @Test
    public void getUserByIdFails() throws Exception {
        utils.regDefaultUser();
        final User expectedUser = userRepository.findAll().get(0);
        utils.perform(get(USERS_PATH + ID_PATH, expectedUser.getId()))
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void getAllUsers() throws Exception {
        utils.regDefaultUser();
        final var response = utils.perform(get(USERS_PATH))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final List<User> users = fromJson(response.getContentAsString(), new TypeReference<>() {
        });

        assertThat(users).hasSize(1);
    }

    @Test
    public void twiceRegTheSameUserFail() throws Exception {
        utils.regDefaultUser().andExpect(status().isCreated());
        utils.regDefaultUser();
        assertEquals(1, userRepository.count());
    }

    @Test
    public void login() throws Exception {
        utils.regDefaultUser();
        final UserLoginDto loginDto = new UserLoginDto(
                utils.getTestNewUserRegistration().getEmail(),
                utils.getTestNewUserRegistration().getPassword()
        );
        final var loginRequest = post(LOGIN_PATH)
                .content(asJson(loginDto))
                .contentType(APPLICATION_JSON);
        utils.perform(loginRequest).andExpect(status().isOk());
    }

    @Test
    public void loginFail() throws Exception {
        final UserLoginDto loginDto = new UserLoginDto(
                utils.getTestNewUserRegistration().getEmail(),
                utils.getTestNewUserRegistration().getPassword()
        );
        final var loginRequest = post(LOGIN_PATH)
                .content(asJson(loginDto))
                .contentType(APPLICATION_JSON);
        utils.perform(loginRequest).andExpect(status().isUnauthorized());
    }

    @Test
    public void updateUser() throws Exception {
        utils.regDefaultUser();

        final User findUser = userRepository.findByEmail(TEST_USERNAME_1);
        final Long userId = findUser.getId();

        final var userDto = new UserRegistrationDto(
                "new name",
                "new last name",
                TEST_USERNAME_2,
                "new pwd"
        );

        final var updateRequest = put(USERS_PATH + ID_PATH, userId)
                .content(asJson(userDto))
                .contentType(APPLICATION_JSON);

        utils.perform(updateRequest, findUser).andExpect(status().isOk());

        assertTrue(userRepository.existsById(userId));
        assertNull(userRepository.findByEmail(TEST_USERNAME_1));
        assertNotNull(userRepository.findByEmail(TEST_USERNAME_2));
    }

    @Test
    public void deleteUser() throws Exception {
        utils.regDefaultUser();

        final User findUser = userRepository.findByEmail(TEST_USERNAME_1);
        final Long userId = findUser.getId();

        utils.perform(delete(USERS_PATH + ID_PATH, userId), findUser)
                .andExpect(status().isOk());

        assertEquals(0, userRepository.count());
    }

    @Test
    public void deleteUserFails() throws Exception {
        utils.regDefaultUser();
        utils.regUser(new UserRegistrationDto(
                "fname",
                "lname",
                TEST_USERNAME_2,
                "pwd"
        ));

        final User findUser = userRepository.findByEmail(TEST_USERNAME_1);
        final User findUser2 = userRepository.findByEmail(TEST_USERNAME_2);
        final Long userId = findUser.getId();

        utils.perform(delete(USERS_PATH + ID_PATH, userId), findUser2)
                .andExpect(status().is4xxClientError());

        assertEquals(2, userRepository.count());
    }
}
