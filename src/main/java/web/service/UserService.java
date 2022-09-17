package web.service;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserService {


    User getUserByEmail(String email);

    List<User> getAllUsers();

    void saveUser(User user);

    User getUser(long id);

    void deleteUser(long id);

    List<Role> getAllRoles();

    Role getRoleById(long id);
}
