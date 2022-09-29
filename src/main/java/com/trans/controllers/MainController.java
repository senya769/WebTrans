package com.trans.controllers;


import com.trans.model.Cargo;
import com.trans.model.Roles;
import com.trans.model.User;
import com.trans.service.CargoService;
import com.trans.service.TransportService;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.Set;

@Controller
public class MainController {

    private final UserService userService;
    private final CargoService cargoService;
    private final TransportService transportService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MainController(UserService userService, CargoService cargoService,
                          TransportService transportService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.cargoService = cargoService;
        this.transportService = transportService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public ModelAndView add(ModelAndView model) {
        model.setViewName("pages/registration");
        return model;
    }

    @GetMapping("/cargo/list")
    protected ModelAndView listCargoAsk() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("cargoListAsk", cargoService.findAllCargoAsk());
        modelAndView.setViewName("pages/cargo/list_all_cargo");
        return modelAndView;
    }

    @GetMapping("/transport/list")
    protected ModelAndView listTransportAsk() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("transportList", transportService.findAll());
        modelAndView.setViewName("pages/transport/list_all_transport");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView add(@ModelAttribute User user, RedirectAttributes attributes) {
        User userByBD = userService.findByEmail(user.getEmail());
        ModelAndView modelAndView = new ModelAndView();
        if (userByBD != null) {
            modelAndView.addObject("message", "User exists!");
            modelAndView.setViewName("pages/registration");
            return modelAndView;

        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Set.of(Roles.USER));
            userService.save(user);
            modelAndView.setViewName("redirect:/login}");
            return modelAndView;
        }
    }

    @GetMapping("/login")
    public ModelAndView loginGet(ModelAndView modelAndView, Authentication authentication) {
        modelAndView.setViewName("pages/login");
        return modelAndView;
    }


    @PostMapping("/successLogin")
    public ModelAndView succesLogin(ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @ModelAttribute("user")
    public User user() {
        return new User();
    }
}
