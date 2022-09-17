package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import web.model.User;
import web.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("userInfo")
    public String showAllUsers(Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("message", "You are successfully logged in!");
        model.addAttribute("userInfo", user);
        return "user-data";
    }

}