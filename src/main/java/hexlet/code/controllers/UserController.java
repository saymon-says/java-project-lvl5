package hexlet.code.controllers;

import hexlet.code.dto.UserRegistrationDto;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import static hexlet.code.controllers.UserController.USERS_PATH;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + USERS_PATH)
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    public static final String ONLY_OWNER_BY_ID = """
                authentication.getName() == @userRepository.findById(#id).get().getEmail()
            """;
    public static final String ID_PATH = "/{id}";
    public static final String USERS_PATH = "/users";

    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User with that id not found")
    })
    @GetMapping(ID_PATH)
    public User getUser(@Parameter(description = "Id of task to be found")
                        @PathVariable final long id) {
        return this.userRepository.findById(id).get();
    }

    @Operation(summary = "Get list of all users")
    @ApiResponse(responseCode = "200", description = "List of all users")
    @GetMapping
    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Operation(summary = "Registration new user")
    @ApiResponse(responseCode = "201", description = "User registered")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registrationUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        this.userService.registerUser(userRegistrationDto);
    }

    @Operation(summary = "Delete user by his id")
    @ApiResponse(responseCode = "200", description = "User deleted")
    @DeleteMapping(ID_PATH)
    @PreAuthorize(ONLY_OWNER_BY_ID)
    public void deleteUser(@Parameter(description = "Id of user to be deleted")
                           @PathVariable final long id) {
        this.userRepository.deleteById(id);
    }

    @Operation(summary = "Update user by his id")
    @ApiResponse(responseCode = "200", description = "User updated")
    @PutMapping(ID_PATH)
    @PreAuthorize(ONLY_OWNER_BY_ID)
    public void updateUser(@Parameter(description = "Id of user to be updated")
                           @PathVariable final long id,
                           @RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        this.userService.updateUser(id, userRegistrationDto);
    }

}
