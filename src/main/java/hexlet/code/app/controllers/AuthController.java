package hexlet.code.app.controllers;

import hexlet.code.app.config.security.JwtTokenUtils;
import hexlet.code.app.dto.UserLoginDto;
import hexlet.code.app.model.User;
import hexlet.code.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${base-url}")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    public static final String LOGIN_PATH = "/login";

    @Operation(summary = "Login by email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authorization"),
            @ApiResponse(responseCode = "401", description = "Not authorized")
    })
    @PostMapping(LOGIN_PATH)
    public ResponseEntity<String> auth(@RequestBody UserLoginDto userLoginDto) {
        User user = userService.findUserByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword());
        if (user == null) {
            return new ResponseEntity<>("Not authorized. Error", HttpStatus.UNAUTHORIZED);
        } else {
            String body = jwtTokenUtils.generateAccessToken(user);
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
    }
}
