package com.trans.controllers;

import com.trans.dto.UserDTO;
import com.trans.model.User;
import com.trans.model.enums.Countries;
import com.trans.service.CargoService;
import com.trans.service.OrderService;
import com.trans.service.TransportService;
import com.trans.service.UserService;
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
   private final UserService userService;
    private final CargoService cargoService;
    private final TransportService transportService;
    private final PasswordEncoder passwordEncoder;
    private final OrderService orderService;
    @Autowired
    public UserController(UserService userService, CargoService cargoService, TransportService transportService, PasswordEncoder passwordEncoder, OrderService orderService) {
        this.userService = userService;
        this.cargoService = cargoService;
        this.transportService = transportService;
        this.passwordEncoder = passwordEncoder;
        this.orderService = orderService;
    }

    @GetMapping("/profile/{id}")
    public ModelAndView profileGet(@PathVariable int id, ModelAndView modelAndView) {
        UserDTO user = userService.findDTOById(id);
        modelAndView.addObject("user", user);
        modelAndView.addObject("countOrderByTransports",orderService.findAllByTransportUserId(id).size());
        modelAndView.setViewName("pages/user/profile");
        return modelAndView;
    }

    @GetMapping("update/{id}")
    public ModelAndView updateGet(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",  userService.findDTOById(id));
        modelAndView.setViewName("/pages/user/update");
        return modelAndView;
    }

    @PostMapping(value = "/update/{id}")
    public RedirectView update(RedirectAttributes redirectAttributes,
                             @RequestParam String firstName,
                             @RequestParam String lastName,
                             @RequestParam String number,
                             @RequestParam String email,
                             @RequestParam String country,
                             @RequestParam String city,
                             @RequestParam String password,
                             @PathVariable int id) {
       UserDTO user = userService.findDTOById(id);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCountry(Countries.fromString(country));
        user.setCity(city);
        user.setNumber(number);
        boolean isUpdate = userService.update(user,password);
        redirectAttributes.addFlashAttribute("isUpdate",isUpdate);
        redirectAttributes.addAttribute("id", id);
        return new RedirectView("/user/profile/{id}",true);
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
        List<UserDTO> users = userService.getAll();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("pages/user/list");
        return modelAndView;
    }

    @ModelAttribute("userUp")
    public User newUser() {
        return new User();
    }
}