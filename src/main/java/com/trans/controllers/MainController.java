package com.trans.controllers;


import com.trans.model.Cargo;
import com.trans.model.Roles;
import com.trans.model.User;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
public class MainController {
    public UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/add")
    public ModelAndView add(ModelAndView model) {
        model.setViewName("pages/registration");
        return model;
    }

    @PostMapping("/add")
    public RedirectView add(@ModelAttribute User user, RedirectAttributes attributes) {
        User userByBD = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        if (userByBD != null) {
            attributes.addFlashAttribute("message", "User exists!");
            redirectView.setUrl("/add");
            return redirectView;

        } else {
            user.setRoles(Roles.USER);
            userService.save(user);
            attributes.addAttribute("id", user.getId());
            redirectView.setUrl("/user/profile/{id}");
            return redirectView;
        }
    }

    @GetMapping("/login")
    public ModelAndView loginGet(ModelAndView modelAndView) {
        modelAndView.setViewName("pages/login");
        return modelAndView;
    }

    @PostMapping("/login")
    public RedirectView loginPost(@RequestParam String email, @RequestParam String password, RedirectAttributes attributes) {
        User user = userService.findByEmailAndPassword(email, password);
        if (user != null) {
            attributes.addAttribute("id", user.getId());
            return new RedirectView("/user/profile/{id}", true);
        } else {
            attributes.addFlashAttribute("error_message", "This username and password combination was not found.");
            //attributes.addAttribute("message","This username and password combination was not found.");
            return new RedirectView("/login", true);
        }
    }

    @ModelAttribute("error_message")
    public String error_message() {
        return null;
    }

    @ModelAttribute("user")
    public User user() {
        return new User();
    }

}
