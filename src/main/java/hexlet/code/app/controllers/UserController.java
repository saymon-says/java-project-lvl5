package hexlet.code.app.controllers;

import hexlet.code.app.dto.UserCreatedDto;
import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;
import hexlet.code.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + "/users")
public class UserController {

    private UserRepository userRepository;

    private UserService userService;

    private PasswordEncoder passwordEncoder;


    @GetMapping("/{id}")
    public final User getUser(@PathVariable long id) {
        return userRepository.findById(id).get();
    }

    @GetMapping
    public final Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public final void createUser(@RequestBody @Valid UserCreatedDto userCreatedDto) {
        userService.createUser(userCreatedDto);
    }

    @DeleteMapping("/{id}")
    public final void deleteUser(@PathVariable long id) {
        userRepository.deleteById(id);
    }

    @PatchMapping("/{id}")
    public final void updateUser(@PathVariable long id, @RequestBody @Valid UserCreatedDto userCreatedDto) {
        userService.updateUser(id, userCreatedDto);
    }

}
