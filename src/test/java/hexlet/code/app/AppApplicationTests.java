package hexlet.code.app;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import hexlet.code.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@DBRider
@DataSet("users.yml")
class AppApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

//    @Test
//    void testRootPage() throws Exception {
//        MockHttpServletResponse response = mockMvc
//                .perform(get("/welcome"))
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(200);
//        assertThat(response.getContentAsString()).contains("Welcome to Spring");
//    }
//
//    @Test
//    void testCreateUser() throws Exception {
//        String content = "{\"firstName\": \"Petr_12\", \"lastName\": \"Petrovich\",
//        \"email\": \"petrilo@yandex.ru\", \"password\": \"mypass\"}";
//
//        MockHttpServletResponse responsePost = mockMvc
//                .perform(
//                        post("/users")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(content)
//                )
//                .andReturn()
//                .getResponse();
//
//        assertThat(responsePost.getStatus()).isEqualTo(200);
//
//        User actualUser = userRepository.findByEmail("petrilo@yandex.ru");
//        assertThat(actualUser).isNotNull();
//        assertThat(actualUser.getFirstName()).isEqualTo("Petr_12");
//    }
//
//    @Test
//    void testGetUsers() throws Exception {
//        MockHttpServletResponse response = mockMvc
//                .perform(get("/users"))
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(200);
//        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
//        assertThat(response.getContentAsString()).contains("sam@gmail.com");
//        assertThat(response.getContentAsString()).contains("andrey@mail.ru");
//    }
//
//    @Test
//    void testGetUser() throws Exception {
//        MockHttpServletResponse response = mockMvc
//                .perform(get("/users/2"))
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(200);
//        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
//        assertThat(response.getContentAsString()).contains("andrey@mail.ru");
//        assertThat(response.getContentAsString()).doesNotContain("sam@gmail.com");
//    }
//
//    @Test
//    void testUpdateUser() throws Exception {
//        String content = "{\"firstName\": \"Nikitos\", \"lastName\": \"Petrovich\",
//        \"email\": \"nikson@mail.ru\", \"password\": \"mypass\"}";
//        MockHttpServletResponse responsePost = mockMvc
//                .perform(
//                        patch("/users/1")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(content)
//                )
//                .andReturn()
//                .getResponse();
//
//        assertThat(responsePost.getStatus()).isEqualTo(200);
//
//        MockHttpServletResponse response = mockMvc
//                .perform(get("/users"))
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(200);
//        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
//        assertThat(response.getContentAsString()).contains("nikson@mail.ru");
//        assertThat(response.getContentAsString()).doesNotContain("sam@gmail.com");
//
//    }
//
//    @Test
//    void testDeleteUser() throws Exception {
//        MockHttpServletResponse responsePost = mockMvc
//                .perform(delete("/users/1"))
//                .andReturn()
//                .getResponse();
//
//        assertThat(responsePost.getStatus()).isEqualTo(200);
//
//        MockHttpServletResponse response = mockMvc
//                .perform(get("/users"))
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(200);
//        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
//        assertThat(response.getContentAsString()).doesNotContain("sam@gmail.com");
//    }
}
