package al.edu.cit.webflix.users.authentication;

import al.edu.cit.webflix.configuration.PasswordEncoder;
import al.edu.cit.webflix.users.User;
import al.edu.cit.webflix.users.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    public User login(AuthenticationRequest request) {
        User user = userDao.getByUsername(request.getUsername());

        if (user == null)
            return null;

        boolean match = passwordEncoder
                .bCryptPasswordEncoder()
                .matches(request.getPassword(), user.getPasswordHash());

        if (!match)
            return null;

        return user;
    }

    public void logout() {

    }
}
