package com.trans.controllers;

import com.trans.model.User;
import com.trans.model.enums.TypeActivity;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.Access;

@Controller
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ModelAndView add(@ModelAttribute User user, @RequestParam String activity) {
        ModelAndView modelAndView = new ModelAndView();
        user.setActivity(TypeActivity.fromString(activity));
        if (userService.save(user) == 0) {
            modelAndView.addObject("message", "User exists!");
            modelAndView.setViewName("pages/registration");
            return modelAndView;
        } else {
            modelAndView.setViewName("redirect:/login}");
            return modelAndView;
        }
    }

    @GetMapping("/registration")
    public ModelAndView pageRegister(ModelAndView modelAndView, @RequestParam String activity) {
        modelAndView.addObject("user", new User());
        if (TypeActivity.fromString(activity) == TypeActivity.INDIVIDUAL) {
            modelAndView.addObject("activity", TypeActivity.INDIVIDUAL);
        }
        modelAndView.setViewName("/pages/registration");
        return modelAndView;
    }
    @PostMapping("/successLogin")
    public ModelAndView successLogin(ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
