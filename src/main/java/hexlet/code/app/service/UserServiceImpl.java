package hexlet.code.app.service;

import hexlet.code.app.dto.UserCreatedDto;
import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserCreatedDto userCreatedDto) {

        User newUser = new User();
        newUser.setFirstName(userCreatedDto.getFirstName());
        newUser.setLastName(userCreatedDto.getLastName());
        newUser.setEmail(userCreatedDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userCreatedDto.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(Long id, UserCreatedDto userCreatedDto) {
        User findUser = userRepository.findById(id).get();
        findUser.setFirstName(userCreatedDto.getFirstName());
        findUser.setLastName(userCreatedDto.getLastName());
        findUser.setPassword(passwordEncoder.encode(userCreatedDto.getPassword()));
        findUser.setEmail(userCreatedDto.getEmail());
        return userRepository.save(findUser);
    }

}
