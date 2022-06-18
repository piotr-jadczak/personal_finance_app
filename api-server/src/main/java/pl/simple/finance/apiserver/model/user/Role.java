package pl.simple.finance.apiserver.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "app_role")
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Getter @Setter
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }
}
