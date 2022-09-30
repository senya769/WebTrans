package com.trans.controllers;


import com.trans.model.Cargo;
import com.trans.model.User;
import com.trans.service.CargoService;
import com.trans.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user/{id}/cargo")
public class CargoController {

    private final CargoService cargoService;
    private final UserRepository userService;

    @Autowired
    public CargoController(CargoService cargoService, UserRepository userService) {
        this.cargoService = cargoService;
        this.userService = userService;
    }


    @GetMapping("/list")
    protected ModelAndView listCargoFromUser(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findById(id);
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
    protected ModelAndView addGet(ModelAndView modelAndView, @PathVariable int id) {
//        User user = userService.findById(id);
        modelAndView.addObject("user_cargo_id", id);
        modelAndView.setViewName("pages/cargo/add_cargo");
        return modelAndView;
    }

    @PostMapping("/add")
    protected ModelAndView addPost(@ModelAttribute Cargo cargo, @PathVariable int id, @RequestParam String dateDeadline) {
        ModelAndView modelAndView = new ModelAndView();
        cargoService.saveWithUserAndDate(cargo,userService.findById(id),dateDeadline);
        modelAndView.addObject("isCreateCargo", true);
        modelAndView.setViewName("pages/cargo/success_add_cargo");
        return modelAndView;
    }

    @GetMapping("/remove/{cargo_id}")
    protected ModelAndView removeCargo(@PathVariable("id") int user_id, @PathVariable int cargo_id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/error/403");
        if (cargoService.deleteById(cargo_id)) {
            modelAndView.setViewName("redirect: /user/" + user_id + "/cargo/list");
        }
        return modelAndView;
    }


    @ModelAttribute("cargo")
    public Cargo newCargo() {
        return new Cargo();
    }
}
