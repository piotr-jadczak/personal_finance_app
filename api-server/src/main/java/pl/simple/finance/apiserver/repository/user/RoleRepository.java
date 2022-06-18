package pl.simple.finance.apiserver.repository.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.simple.finance.apiserver.model.user.ERole;
import pl.simple.finance.apiserver.model.user.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}
