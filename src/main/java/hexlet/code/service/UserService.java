package hexlet.code.service;

import hexlet.code.dto.UserRegistrationDto;
import hexlet.code.model.User;

public interface UserService {

    User registerUser(UserRegistrationDto userRegistrationDto);

    User updateUser(Long id, UserRegistrationDto userRegistrationDto);

    User findUserByEmailAndPassword(String email, String password);

    User findByToken();
}
