package al.edu.cit.webflix.users.authentication;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;

    private String password;
}
