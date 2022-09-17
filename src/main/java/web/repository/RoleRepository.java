package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role getRoleById(long id);
    List<Role> getRolesById(long id);

}
