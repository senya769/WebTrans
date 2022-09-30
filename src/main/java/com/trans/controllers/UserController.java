package com.trans.controllers;

import com.trans.model.User;
import com.trans.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
   private final UserRepository userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserController(UserRepository userService) {
        this.userService = userService;
    }

    @GetMapping("/profile/{id}")
    public ModelAndView profileGet(@PathVariable int id, ModelAndView modelAndView) {
        User user = userService.findById(id);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("pages/user/profile");
        return modelAndView;
    }

    @GetMapping("update/{id}")
    public ModelAndView updateGet(@PathVariable int id) {
        User user = userService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/pages/user/update");
        return modelAndView;
    }

    @PostMapping(value = "/update/{id}")
    public RedirectView test(RedirectView view, RedirectAttributes redirectAttributes, @RequestParam String email, @RequestParam String password,
                             @RequestParam String numberPhone, @RequestParam String status, @RequestParam String nickname,
                             @PathVariable int id) {
        User user = userService.findById(id);
        user.setEmail(email);
        user.setNickname(nickname);
        user.setNumberPhone(numberPhone);
        user.setPassword(passwordEncoder.encode(password));
        user.setStatus(status);
        userService.save(user);
        redirectAttributes.addAttribute("id", id);
        view.setContextRelative(true);
        view.setUrl("/user/profile/{id}");
        return view;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteGet(ModelAndView modelAndView, @PathVariable int id) {
        userService.deleteById(id);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deletePost(ModelAndView modelAndView, @PathVariable int id) {
        userService.deleteById(id);
        modelAndView.setViewName("redirect:/user/list");
        return modelAndView;
    }

    @GetMapping("/list")
    public ModelAndView listUsers(ModelAndView modelAndView) {
        List<User> users = userService.getAll();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("pages/user/list");
        return modelAndView;
    }

    @ModelAttribute("userUp")
    public User newUser() {
        return new User();
    }
}