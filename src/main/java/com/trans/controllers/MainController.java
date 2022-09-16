package com.trans.controllers;


import com.trans.model.Cargo;
import com.trans.model.Roles;
import com.trans.model.User;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

@Controller
public class MainController {

    public UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public ModelAndView add(ModelAndView model) {
        model.setViewName("pages/registration");
        return model;
    }

    @PostMapping("/registration")
    public ModelAndView add(@ModelAttribute User user, RedirectAttributes attributes) {
        User userByBD = userService.findByEmail(user.getEmail());
        ModelAndView modelAndView = new ModelAndView();
        if (userByBD != null) {
            attributes.addFlashAttribute("message", "User exists!");
            modelAndView.setViewName("/registration");
            return modelAndView;

        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(new HashSet<>(Collections.singleton(Roles.USER)));
            userService.save(user);
            modelAndView.setViewName("redirect:/login}");
            return modelAndView;
        }
    }

    @GetMapping("/login")
    public ModelAndView loginGet(ModelAndView modelAndView) {
            modelAndView.setViewName("pages/login");
        return modelAndView;
    }


/*    @PostMapping("/login")
    public RedirectView loginPost(@RequestParam("username") String email, @RequestParam String password, RedirectAttributes attributes) {
        User user = userService.findByEmailAndPassword(email, password);
        if (user != null) {
            attributes.addAttribute("id", user.getId());
            return new RedirectView("/user/profile/{id}", true);
        } else {
            attributes.addFlashAttribute("error_message", "This username and password combination was not found.");
            //attributes.addAttribute("message","This username and password combination was not found.");
            return new RedirectView("/login", true);
        }
    }*/

    @GetMapping("/test")
    public ModelAndView test(ModelAndView modelAndView) {
        modelAndView.setViewName("pages/test");
        return modelAndView;
    }
    @GetMapping("/about")
    public ModelAndView about(ModelAndView modelAndView) {
        modelAndView.setViewName("pages/test");
        return modelAndView;
    }
    @PostMapping("/test")
    public ModelAndView test1(ModelAndView modelAndView) {
        modelAndView.setViewName("pages/test");
        return modelAndView;
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
