package ru.Technopolis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.Technopolis.domain.User;
import ru.Technopolis.service.SecurityService;
import ru.Technopolis.service.UserService;

@Controller
public class UserController {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(User user, Model model) {
        if (userService.loadUserByUsername(user.getUsername()) != null) {
            model.addAttribute("message", "User exists!");
            return "registration";
        } else {
            String userPassword = user.getPassword();
            userService.save(user);
            securityService.autoLogin(user.getUsername(), userPassword);
            return "redirect:/todos";
        }
    }

}