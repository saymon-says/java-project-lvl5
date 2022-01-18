package hexlet.code.service;

import hexlet.code.dto.UserRegistrationDto;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
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
    public User registerUser(UserRegistrationDto userRegistrationDto) {

        User newUser = new User();
        User findUser = userRepository.findByEmail(userRegistrationDto.getEmail());
        if (findUser == null) {
            return fillInUser(userRegistrationDto, newUser);
        }
        return null;
    }

    @Override
    public User updateUser(Long id, UserRegistrationDto userRegistrationDto) {
        User findUser = userRepository.findById(id).get();
        return fillInUser(userRegistrationDto, findUser);
    }

    private User fillInUser(UserRegistrationDto userRegistrationDto, User findUser) {
        findUser.setFirstName(userRegistrationDto.getFirstName());
        findUser.setLastName(userRegistrationDto.getLastName());
        findUser.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        findUser.setEmail(userRegistrationDto.getEmail());
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
