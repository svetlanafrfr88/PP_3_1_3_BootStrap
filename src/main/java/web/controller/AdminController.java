package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("userList")
    public String showAllUsers(Model model, Principal principal) {
        model.addAttribute("users", userService.getAllUsers());
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("userInfo", user);
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", userService.getAllRoles());
        return "all-users";
    }

    @GetMapping("addNewUser")
    public String addNewUser(Model model) {
        User user = new User();
        List<Role> roleList = userService.getAllRoles();
        model.addAttribute("newUser", user);
        model.addAttribute("roles", roleList);
        return "user-info";
    }

    @PostMapping(value = "saveUser")
    public String saveUser(@ModelAttribute("newUser") User user, @RequestParam(value = "role", required = false) String[] role) {
        Set<Role> roleSet = new HashSet<>();
        for (String roles : role) {
            roleSet.add(userService.getRoleById(Long.parseLong(roles)));
        }
        user.setRoles(roleSet);
        if (user.getId() == null) {
            user.setPassword(passwordEncoder().encode(user.getPassword()));
            userService.saveUser(user);
        }
        userService.saveUser(user);
        return "redirect:/admin/userList";
    }

    @PostMapping(value = "updateUser")
    public String updateUser(@ModelAttribute User userEdit, @RequestParam(value = "role", required = false) String[] role,
                             @RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "lastname", required = false) String lastname,
                             @RequestParam(value = "password", required = false) String password,
                             @RequestParam(value = "email", required = false) String email,
                             @RequestParam(value = "age", required = false) Integer age,
                             @RequestParam(value = "firstname", required = false) String firstname) {
        Set<Role> roleSet = new HashSet<>();
        for (String roles : role) {
            roleSet.add(userService.getRoleById(Long.parseLong(roles)));
        }
        userEdit.setRoles(roleSet);
        userEdit.setId(id);
        userEdit.setFirstname(firstname);
        userEdit.setLastname(lastname);
        userEdit.setPassword(password);
        userEdit.setAge(age);
        userEdit.setEmail(email);
        if (userEdit.getId() == null) {
            userEdit.setPassword(passwordEncoder().encode(userEdit.getPassword()));
            userService.saveUser(userEdit);
        }
        userService.saveUser(userEdit);
        return "redirect:/admin/userList";
    }

    @PostMapping("deleteUser/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/userList";
    }

}
