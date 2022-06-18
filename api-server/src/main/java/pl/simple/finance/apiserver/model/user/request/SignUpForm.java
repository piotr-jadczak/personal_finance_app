package pl.simple.finance.apiserver.model.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class SignUpForm {

    @Size(min = 4, max = 20)
    @NotBlank
    private String username;

    @Email
    @Size(max = 64)
    @NotBlank
    private String email;

    @Size(min = 8, max = 40)
    @NotBlank
    private String password;
}
