package hexlet.code.app.service;

import hexlet.code.app.dto.UserCreatedDto;
import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User createUser(UserCreatedDto userCreatedDto) {

        User newUser = new User();
        newUser.setFirstName(userCreatedDto.getFirstName());
        newUser.setLastName(userCreatedDto.getLastName());
        newUser.setEmail(userCreatedDto.getEmail());
        newUser.setPassword(userCreatedDto.getPassword());
        return userRepository.save(newUser);
    }

}
