package hexlet.code.app.service;

import hexlet.code.app.dto.UserCreatedDto;
import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        User findUser = userRepository.findByEmail(userCreatedDto.getEmail());
        if (findUser == null) {
            return fillInUser(userCreatedDto, newUser);
        }
        return null;
    }

    @Override
    public User updateUser(Long id, UserCreatedDto userCreatedDto) {
        User findUser = userRepository.findById(id).get();
        return fillInUser(userCreatedDto, findUser);
    }

    private User fillInUser(UserCreatedDto userCreatedDto, User findUser) {
        findUser.setFirstName(userCreatedDto.getFirstName());
        findUser.setLastName(userCreatedDto.getLastName());
        findUser.setPassword(passwordEncoder.encode(userCreatedDto.getPassword()));
        findUser.setEmail(userCreatedDto.getEmail());
        return userRepository.save(findUser);
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByToken() {
        return userRepository.findByEmail(getUserNameByToken());
    }

    private String getUserNameByToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }
}
