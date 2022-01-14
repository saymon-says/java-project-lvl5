package hexlet.code.app.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.app.dto.UserRegistrationDto;
import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static hexlet.code.app.controllers.UserController.USERS_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
public class TestUtils {

    private static final String TEST_USERNAME_1 = "test_1@gmail.com";
    private static final String TEST_USERNAME_2 = "test_2@gmail.com";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private final UserRegistrationDto testNewUserRegistration = new UserRegistrationDto(
            "Sam", "Testov", TEST_USERNAME_1, "1234");

    public UserRegistrationDto getTestNewUserRegistration() {
        return testNewUserRegistration;
    }

    public void tearDown() {
        userRepository.deleteAll();
    }

    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    public ResultActions regDefaultUser() throws Exception {
        return regUser(testNewUserRegistration);
    }

    public ResultActions regUser(final UserRegistrationDto dto) throws Exception {
        final var request = post(USERS_PATH)
                .content(asJson(dto))
                .contentType(APPLICATION_JSON);

        return perform(request);
    }

//    public ResultActions perform(final MockHttpServletRequestBuilder request, final String byUser) throws Exception {
//        final String token = .expiring(Map.of("username", byUser));
//        request.header(AUTHORIZATION, token);
//
//        return perform(request);
//    }

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
