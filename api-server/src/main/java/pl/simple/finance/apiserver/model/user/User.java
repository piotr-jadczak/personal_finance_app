package pl.simple.finance.apiserver.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.simple.finance.apiserver.model.saving.Savings;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "app_user")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Size(min = 4, max = 20)
    @NotBlank
    @Column(unique = true)
    @Getter @Setter
    private String username;

    @Email
    @Size(max = 64)
    @NotBlank
    @Column(unique = true)
    @Getter @Setter
    private String email;

    @Size(min = 8, max = 120)
    @NotBlank
    @Getter @Setter
    private String password;

    @ManyToMany
    @Getter @Setter
    private Set<Role> roles = new HashSet<>();

    @OneToOne
    @Getter @Setter
    private Savings savings;

    public User(String username, String email, String password,
                Set<Role> roles, Savings savings) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.savings = savings;
    }
}
