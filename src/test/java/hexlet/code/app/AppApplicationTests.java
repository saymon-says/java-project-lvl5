package hexlet.code.app;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DBRider
@DataSet("users.yml")
class AppApplicationTests {

//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private UserRepository userRepository;
//
//    private static final int OK = 200;
//
//    @Test
//    void testRootPage() throws Exception {
//        MockHttpServletResponse response = mockMvc
//                .perform(get("/welcome"))
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(OK);
//        assertThat(response.getContentAsString()).contains("Welcome to Spring");
//    }
//
//    @Test
//    void testCreateUser() throws Exception {
//        String content = "{\"firstName\": \"Petr_12\", \"lastName\": \"Petrovich\", "
//                + "\"email\": \"petrilo@yandex.ru\", \"password\": \"mypass\"}";
//
//        MockHttpServletResponse responsePost = mockMvc
//                .perform(
//                        post("/api/users")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(content)
//                )
//                .andReturn()
//                .getResponse();
//
//        assertThat(responsePost.getStatus()).isEqualTo(OK);
//
//        User actualUser = userRepository.findByEmail("petrilo@yandex.ru");
//        assertThat(actualUser).isNotNull();
//        assertThat(actualUser.getFirstName()).isEqualTo("Petr_12");
//    }
//
//    @Test
//    void testGetUsers() throws Exception {
//        MockHttpServletResponse response = mockMvc
//                .perform(get("/api/users"))
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(OK);
//        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
//        assertThat(response.getContentAsString()).contains("sam@gmail.com");
//        assertThat(response.getContentAsString()).contains("andrey@mail.ru");
//    }
//
//    @Test
//    void testGetUser() throws Exception {
//        MockHttpServletResponse response = mockMvc
//                .perform(get("/api/users/200"))
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(OK);
//        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
//        assertThat(response.getContentAsString()).contains("andrey@mail.ru");
//        assertThat(response.getContentAsString()).doesNotContain("sam@gmail.com");
//    }
//
//    @Test
//    void testUpdateUser() throws Exception {
//        String content = "{\"firstName\": \"Nikitos\", \"lastName\": \"Petrovich\", "
//                + "\"email\": \"nikson@mail.ru\", \"password\": \"mypass\"}";
//        MockHttpServletResponse responsePost = mockMvc
//                .perform(
//                        patch("/api/users/100")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(content)
//                )
//                .andReturn()
//                .getResponse();
//
//        assertThat(responsePost.getStatus()).isEqualTo(OK);
//
//        MockHttpServletResponse response = mockMvc
//                .perform(get("/api/users"))
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(OK);
//        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
//        assertThat(response.getContentAsString()).contains("nikson@mail.ru");
//        assertThat(response.getContentAsString()).doesNotContain("sam@gmail.com");
//
//    }
//
//    @Test
//    void testDeleteUser() throws Exception {
//        MockHttpServletResponse responsePost = mockMvc
//                .perform(delete("/api/users/100"))
//                .andReturn()
//                .getResponse();
//
//        assertThat(responsePost.getStatus()).isEqualTo(OK);
//
//        MockHttpServletResponse response = mockMvc
//                .perform(get("/api/users"))
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(OK);
//        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
//        assertThat(response.getContentAsString()).doesNotContain("sam@gmail.com");
//    }
//
//    @Test
//    void testUnauthorized() throws Exception {
//        MockHttpServletResponse response = mockMvc
//                .perform(get("/api/users/200"))
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(401);
//    }
}
