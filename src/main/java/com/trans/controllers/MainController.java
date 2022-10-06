package com.trans.controllers;


import com.trans.model.Cargo;
import com.trans.model.User;
import com.trans.model.enums.TypeActivity;
import com.trans.service.CargoService;
import com.trans.service.TransportService;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.print.Book;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {

    private final UserService userService;
    private final CargoService cargoService;
    private final TransportService transportService;


    @Autowired
    public MainController(UserService userService, CargoService cargoService, TransportService transportService) {
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
      //  Page<Cargo> cargoPage =  cargoService.findAllSortByDateCreated(page);
      //  modelAndView.addObject("cargoListAsk",cargoPage.getContent());
        modelAndView.addObject("cargoListAsk",cargoService.findAllSortByDateCreated(page));
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

}
