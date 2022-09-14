package com.trans.controllers;


import com.trans.model.Cargo;
import com.trans.model.User;
import com.trans.service.CargoService;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/user/{id}/cargo")
public class CargoController {

    private final CargoService cargoService;
    private final UserService userService;

    @Autowired
    public CargoController(CargoService cargoService, UserService userService) {
        this.cargoService = cargoService;
        this.userService = userService;
    }

    @GetMapping("/list")
    protected ModelAndView list(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findById(id);
        List<Cargo> cargoList = user.getCargoList();
        modelAndView.addObject("cargoList", cargoList);
        modelAndView.addObject("userList", userService.getAll());
        if (!cargoList.isEmpty()) {
            modelAndView.setViewName("pages/cargo/list_cargo");
        } else {
            modelAndView.addObject("isNotFoundCargo", "Cargo is not exits!");
            modelAndView.setViewName("redirect: /user/profile/"+id);
        }
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
    protected ModelAndView addPost(@ModelAttribute Cargo cargo, @PathVariable int id,
                                   RedirectAttributes attributes, @RequestParam String local_date_deadline) {
        ModelAndView modelAndView = new ModelAndView();
        LocalDateTime dateDeadline = LocalDateTime.parse(local_date_deadline);
        User user = userService.findById(id);
        cargo.setLocalDateDeadline(dateDeadline);
        cargo.setUser(user);
        cargoService.save(cargo);
        modelAndView.addObject("isCreateCargo", true);
        //        attributes.addAttribute("id", id);
        modelAndView.setViewName("pages/cargo/success_add_cargo");
        return modelAndView;
    }

 @GetMapping("/remove/{id_cargo}")
 protected ModelAndView removeCargo(@PathVariable("id") int id_user,@PathVariable int id_cargo){
     ModelAndView modelAndView = new ModelAndView();
     if(cargoService.deleteCargoById(id_cargo)){
         modelAndView.addObject("isDelete",true);
         modelAndView.setViewName("forward:/user/profile/" + id_user);
     }
     else {
         modelAndView.addObject("isDelete",false);
         modelAndView.setViewName("redirect:/error");
     }
     return modelAndView;
 }

    @ModelAttribute("cargo")
    public Cargo newCargo() {
        return new Cargo();
    }
}
