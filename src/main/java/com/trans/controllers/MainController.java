package com.trans.controllers;


import com.trans.model.User;
import com.trans.service.CargoService;
import com.trans.service.TransportService;
import com.trans.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    private final UserRepository userService;
    private final CargoService cargoService;
    private final TransportService transportService;


    @Autowired
    public MainController(UserRepository userService, CargoService cargoService, TransportService transportService) {
        this.userService = userService;
        this.cargoService = cargoService;
        this.transportService = transportService;
    }

    @GetMapping("/cargo/list")
    protected ModelAndView listCargoAsk() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("cargoList", cargoService.findAllSortByDateCreated());
        modelAndView.setViewName("pages/cargo/list_all_cargo");
        return modelAndView;
    }
    @PostMapping("/cargo/list")
    protected ModelAndView listCargoAskPost(@RequestParam(defaultValue = "1") int page) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("cargoListAsk", cargoService.findAllSortByDateCreated(page));
        modelAndView.addObject("pageList", cargoService.findAllSortByDateCreated(page).size());
        modelAndView.addObject("pageActive", page);
        modelAndView.setViewName("pages/cargo/list_all_cargo");
        return modelAndView;
    }

    @GetMapping("/transport/list")
    protected ModelAndView listTransportAsk(@RequestParam(defaultValue = "1") int page) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("transportList", transportService.findAll());
        modelAndView.setViewName("pages/transport/list_all_transport");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView add(@ModelAttribute User user, RedirectAttributes attributes) {
        ModelAndView modelAndView = new ModelAndView();
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
    public ModelAndView pageRegistr(ModelAndView modelAndView) {
        modelAndView.addObject("user",new User());
        modelAndView.setViewName("/pages/registration");
        return modelAndView;
    }


    @PostMapping("/successLogin")
    public ModelAndView succesLogin(ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }


}
