package hexlet.code.app.controllers;

import hexlet.code.app.dto.UserCreatedDto;
import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;
import hexlet.code.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + "/users")
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    public static final String ONLY_OWNER_BY_ID = """
                authentication.getName() == @userRepository.findById(#id).get().getEmail()
            """;
    public static final String ID_PATH = "/{id}";

    @GetMapping(ID_PATH)
    public User getUser(@PathVariable long id) {
        return userRepository.findById(id).get();
    }

    @GetMapping
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid UserCreatedDto userCreatedDto) {
        userService.createUser(userCreatedDto);
    }

    @DeleteMapping(ID_PATH)
    @PreAuthorize(ONLY_OWNER_BY_ID)
    public void deleteUser(@PathVariable long id) {
        userRepository.deleteById(id);
    }

    @PutMapping(ID_PATH)
    @PreAuthorize(ONLY_OWNER_BY_ID)
    public void updateUser(@PathVariable long id, @RequestBody @Valid UserCreatedDto userCreatedDto) {
        userService.updateUser(id, userCreatedDto);
    }

}
