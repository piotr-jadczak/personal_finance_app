package pl.simple.finance.apiserver.model.user.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class JwtResponse {

    @Getter @Setter
    private String token;
    @Getter @Setter
    private String type = "Bearer";
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String email;
    @Getter
    private List<String> roles;

    public JwtResponse(String token, Long id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
