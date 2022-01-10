package hexlet.code.app.service;

import hexlet.code.app.dto.UserRegistrationDto;
import hexlet.code.app.model.User;

public interface UserService {

    User registerUser(UserRegistrationDto userRegistrationDto);

    User updateUser(Long id, UserRegistrationDto userRegistrationDto);

    User findUserByEmailAndPassword(String email, String password);

    User findByToken();
}
