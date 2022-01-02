package hexlet.code.app.controllers;

import hexlet.code.app.config.security.JwtTokenUtils;
import hexlet.code.app.dto.UserCreatedDto;
import hexlet.code.app.model.User;
import hexlet.code.app.service.UserService;
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

    @PostMapping("/login")
    public ResponseEntity<String> auth(@RequestBody UserCreatedDto userCreatedDto) {
        User user = userService.findUserByEmailAndPassword(userCreatedDto.getEmail(), userCreatedDto.getPassword());
        if (user == null) {
            return new ResponseEntity<>("Not authorized. Error", HttpStatus.UNAUTHORIZED);
        } else {
            String body = jwtTokenUtils.generateAccessToken(user);
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
    }
}
