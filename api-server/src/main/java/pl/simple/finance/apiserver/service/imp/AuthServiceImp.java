package pl.simple.finance.apiserver.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.simple.finance.apiserver.model.saving.Savings;
import pl.simple.finance.apiserver.model.user.ERole;
import pl.simple.finance.apiserver.model.user.Role;
import pl.simple.finance.apiserver.model.user.User;
import pl.simple.finance.apiserver.model.user.request.SignUpForm;
import pl.simple.finance.apiserver.model.user.response.JwtResponse;
import pl.simple.finance.apiserver.repository.user.RoleRepository;
import pl.simple.finance.apiserver.repository.user.UserRepository;
import pl.simple.finance.apiserver.security.JwtUtils;
import pl.simple.finance.apiserver.security.UserDetailsImp;
import pl.simple.finance.apiserver.service.contract.AuthService;
import pl.simple.finance.apiserver.service.contract.SavingsService;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthServiceImp implements AuthService {


    @Autowired
    private AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final SavingsService savingsService;

    @Autowired
    public AuthServiceImp(
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder encoder,
                          JwtUtils jwtUtils,
                          SavingsService savingsService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.savingsService = savingsService;
    }

    @Override
    public Authentication getAuthenticationToken(String username, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
    }

    @Override
    public JwtResponse getJwtResponse(Authentication authenticationToken) {

        String jwt = jwtUtils.generateJwtToken(authenticationToken);
        UserDetailsImp userDetails = (UserDetailsImp) authenticationToken.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
                                userDetails.getEmail(), roles);
    }

    @Override
    public User registerUser(SignUpForm signUpForm) {

        Set<Role> userRoles = new HashSet<>();
        Role role = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role user not found."));
        userRoles.add(role);

        Savings userSavings = savingsService.createEmptySavings();

        User newUser = new User(signUpForm.getUsername(),
                                signUpForm.getEmail(),
                                encoder.encode(signUpForm.getPassword()),
                                userRoles,
                                userSavings);

        return userRepository.save(newUser);
    }

    @Override
    public boolean userExistByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean userExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
