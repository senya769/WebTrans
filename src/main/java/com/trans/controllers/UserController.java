package com.trans.controllers;

import com.trans.dto.UserDTO;
import com.trans.model.User;
import com.trans.model.enums.Countries;
import com.trans.model.enums.TypeUser;
import com.trans.model.util.CustomUserDetails;
import com.trans.service.CargoService;
import com.trans.service.OrderService;
import com.trans.service.TransportService;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;


@RestController
@RequestMapping("/users")
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

    //todo get size ts/cargo with status isDelete = false and isFree = true
    @GetMapping("/{user_id}/profile")
    public ModelAndView profileGet(@PathVariable int user_id, ModelAndView modelAndView) {
        UserDTO user = userService.findActiveDTOById(user_id);
        if(user != null) {
            modelAndView.addObject("user", user);
            modelAndView.addObject("transportSentOrders", orderService.getTransportSentOrdersById(user_id, 0).getContent());
            modelAndView.addObject("cargoSentOrders", orderService.getCargoSentOrdersById(user_id, 0).getContent());
            modelAndView.addObject("transportReceivedOrders", orderService.getTransportReceivedOrdersById(user_id, 0).getContent());
            modelAndView.addObject("cargoReceivedOrders", orderService.getCargoReceivedOrdersById(user_id, 0).getContent());
            modelAndView.setViewName("pages/user/profile");
        }
        else
            modelAndView.setViewName("/pages/error/403");
        return modelAndView;
    }

    @GetMapping("{user_id}/update")
    public ModelAndView updateGet(@PathVariable int user_id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("user", userService.findDTOById(user_id));
            modelAndView.setViewName("pages/user/update");
        return modelAndView;
    }

    @PostMapping(value = "/{user_id}/update")
    public RedirectView update(RedirectAttributes redirectAttributes,
                               @RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String number,
                               @RequestParam String email,
                               @RequestParam String country,
                               @RequestParam String city,
                               @RequestParam String password,
                               @RequestParam String type,
                               @PathVariable int user_id) {
        UserDTO user = userService.findDTOById(user_id);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCountry(Countries.fromString(country));
        user.setCity(city);
        user.setNumber(number);
        user.setType(TypeUser.fromString(type));
        boolean isUpdate = userService.update(user, password);
        redirectAttributes.addFlashAttribute("isUpdate", isUpdate);
        redirectAttributes.addAttribute("user_id", user_id);
        return new RedirectView("/users/{user_id}/profile", true);

    }

    @GetMapping("/{user_id}/delete")
    public ModelAndView deleteGet(ModelAndView modelAndView, @PathVariable int user_id) {
            userService.deleteById(user_id);
            modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping()
    public ModelAndView listUsers(ModelAndView modelAndView) {
        List<UserDTO> users = userService.getAll();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("pages/user/list");
        return modelAndView;
    }

    @GetMapping("/{user_id}/update/image")
    public ModelAndView editImage(ModelAndView modelAndView, @PathVariable Integer user_id,
                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails.getId() == user_id) {
            User user = userService.findById(user_id);
            modelAndView.addObject("user", user);
            modelAndView.setViewName("pages/user/edit_image");
        } else {
            modelAndView.addObject("id", user_id);
            modelAndView.setViewName("pages/error/405");
        }
        return modelAndView;
    }

    @PostMapping("/{user_id}/update/image")
    public ModelAndView editImage(ModelAndView modelAndView, @RequestParam String imageURL, @PathVariable Integer user_id) {
        UserDTO userDTO = userService.findDTOById(user_id);
        userDTO.setImageURL(imageURL);
        userService.update(userDTO);
        modelAndView.setViewName("redirect:/users/" + user_id + "/profile");
        return modelAndView;
    }

    @ModelAttribute("userUp")
    public User newUser() {
        return new User();
    }
}