package hexlet.code.app.service;

import hexlet.code.app.dto.UserCreatedDto;
import hexlet.code.app.model.User;

public interface UserService {

    User createUser(UserCreatedDto userCreatedDto);

    User updateUser(Long id, UserCreatedDto userCreatedDto);
}
