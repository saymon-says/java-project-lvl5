package hexlet.code.app.service;

import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository pUserRepository) {
        this.userRepository = pUserRepository;
    }

    @Override
    public final UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("user"));

        User user = userRepository.findByEmail(email);

        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), authorities);
    }
}
