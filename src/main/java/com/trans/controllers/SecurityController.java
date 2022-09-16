package com.trans.controllers;

import com.trans.model.User;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@RestController
@RequestMapping("/sec")
public class SecurityController {

    private final UserService userService;

    @Autowired
    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String princ(Principal principal) {
        return principal.toString();
    }

    @PostMapping
    public ModelAndView princPost(Principal principal, ModelAndView modelAndView) {
        User user = userService.findByEmail(principal.getName());
        modelAndView.addObject("user",user);
        modelAndView.setViewName("pages/test");
        return modelAndView;
    }
}
