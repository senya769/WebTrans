package com.trans.controllers;


import com.trans.dto.UserDTO;
import com.trans.model.Cargo;
import com.trans.model.util.CustomUserDetails;
import com.trans.service.CargoService;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/users/{user_id}/cargo")
public class CargoController {

    private final CargoService cargoService;
    private final UserService userService;

    @Autowired
    public CargoController(CargoService cargoService, UserService userService) {
        this.cargoService = cargoService;
        this.userService = userService;
    }


    @GetMapping()
    protected ModelAndView listCargoFromUser( ModelAndView modelAndView,
                                              @RequestParam(defaultValue = "1") int page,
                                              @PathVariable int user_id) {
        UserDTO user = userService.findDTOById(user_id);
        if (user.getCargoList().isEmpty()) {
            modelAndView.addObject("notExists", true);
        } else {
            modelAndView.addObject("notExists", false);
        }
        Page<Cargo> allActiveByUserId = cargoService.findAllActiveByUserId(user_id, page);
        modelAndView.addObject("user", user);
        modelAndView.addObject("cargoPage", page);
        modelAndView.addObject("listActiveCargo",allActiveByUserId.getContent());
        int totalPages = allActiveByUserId.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.setViewName("pages/cargo/list");
        return modelAndView;
    }

    @GetMapping("/add")
    protected ModelAndView addGet(ModelAndView modelAndView, @PathVariable int user_id) {
        modelAndView.addObject("user_id", user_id);
        modelAndView.setViewName("pages/cargo/add");
        return modelAndView;
    }

    @PostMapping("/add")
    protected RedirectView addPost(@ModelAttribute Cargo cargo, RedirectAttributes attributes,
                                   @PathVariable int user_id, @RequestParam String dateDeadline) {
        cargoService.saveWithUserAndDate(cargo, userService.findDTOById(user_id), dateDeadline);
        attributes.addAttribute("user_id", user_id);
        attributes.addFlashAttribute("isCreateCargo", true);
       return new RedirectView("/users/{user_id}/profile",true);
    }

    @GetMapping("/remove/{cargo}")
    protected RedirectView removeCargo(RedirectAttributes redirectAttributes,
                                       @PathVariable("user_id") int user_id, @PathVariable("cargo") int cargo_id) {
        redirectAttributes.addAttribute("user_id", user_id);
        if (cargoService.deleteById(cargo_id)) {
            redirectAttributes.addFlashAttribute("cargoIsDelete", true);
        } else {
            redirectAttributes.addFlashAttribute("cargoIsNotDelete", true);
        }
        return new RedirectView("/users/{user_id}/profile",true);

    }

    @ModelAttribute("cargo")
    public Cargo newCargo() {
        return new Cargo();
    }
}
