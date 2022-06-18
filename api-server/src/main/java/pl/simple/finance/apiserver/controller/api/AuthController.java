package pl.simple.finance.apiserver.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.simple.finance.apiserver.model.user.User;
import pl.simple.finance.apiserver.model.user.request.LoginForm;
import pl.simple.finance.apiserver.model.user.request.SignUpForm;
import pl.simple.finance.apiserver.model.user.response.MessageResponse;
import pl.simple.finance.apiserver.service.contract.AuthService;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginForm) {

        Authentication authentication = authService
                .getAuthenticationToken(loginForm.getUsername(), loginForm.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(authService.getJwtResponse(authentication).getToken());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpForm) {

        if(authService.userExistByUsername(signUpForm.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Username is already taken."));
        }
        if(authService.userExistByEmail(signUpForm.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email is already taken."));
        }
        User registeredUser = authService.registerUser(signUpForm);
        if(registeredUser ==  null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error while creating an user!"));
        }
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
