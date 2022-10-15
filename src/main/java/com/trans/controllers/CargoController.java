package com.trans.controllers;


import com.trans.dto.UserDTO;
import com.trans.model.Cargo;
import com.trans.service.CargoService;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/user/{user_id}/cargo")
public class CargoController {

    private final CargoService cargoService;
    private final UserService userService;

    @Autowired
    public CargoController(CargoService cargoService, UserService userService) {
        this.cargoService = cargoService;
        this.userService = userService;
    }


    @GetMapping("/list")
    protected ModelAndView listCargoFromUser(@PathVariable int user_id) {
        ModelAndView modelAndView = new ModelAndView();
        UserDTO user = userService.findDTOById(user_id);
        if (user.getCargoList().isEmpty()) {
            modelAndView.addObject("notExists", true);
        } else {
            modelAndView.addObject("notExists", false);
        }
        modelAndView.addObject("user", user);
        modelAndView.setViewName("pages/cargo/list_cargo");
        return modelAndView;
    }

    @GetMapping("/add")
    protected ModelAndView addGet(ModelAndView modelAndView, @PathVariable int user_id) {
        modelAndView.addObject("user_id", user_id);
        modelAndView.setViewName("pages/cargo/add_cargo");
        return modelAndView;
    }

    @PostMapping("/add")
    protected RedirectView addPost(@ModelAttribute Cargo cargo, RedirectAttributes attributes,
                                   @PathVariable int user_id, @RequestParam String dateDeadline) {
        cargoService.saveWithUserAndDate(cargo, userService.findDTOById(user_id), dateDeadline);
        attributes.addAttribute("user_id", user_id);
        attributes.addFlashAttribute("isCreateCargo", true);
       return new RedirectView("/user/profile/{user_id}",true);
    }


    @GetMapping("/remove/{cargo}")
    protected RedirectView removeCargo(RedirectAttributes redirectAttributes,
                                       @PathVariable("user_id") int id_user, @PathVariable("cargo") int cargo_id) {
        if (cargoService.deleteById(cargo_id)) {
            redirectAttributes.addFlashAttribute("cargoIsDelete", true);
        } else {
            redirectAttributes.addFlashAttribute("isNotFoundCargo", true);
        }
        return new RedirectView("/user/profile/" + id_user,true);
    }

    @ModelAttribute("cargo")
    public Cargo newCargo() {
        return new Cargo();
    }
}
