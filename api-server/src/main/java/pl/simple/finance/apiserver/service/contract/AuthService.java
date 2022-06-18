package pl.simple.finance.apiserver.service.contract;

import org.springframework.security.core.Authentication;
import pl.simple.finance.apiserver.model.user.User;
import pl.simple.finance.apiserver.model.user.request.SignUpForm;
import pl.simple.finance.apiserver.model.user.response.JwtResponse;

public interface AuthService {

    Authentication getAuthenticationToken(String username, String password);
    JwtResponse getJwtResponse(Authentication authenticationToken);
    User registerUser(SignUpForm signUpForm);
    boolean userExistByUsername(String username);
    boolean userExistByEmail(String email);
}
