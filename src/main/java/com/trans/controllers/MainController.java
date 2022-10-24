package com.trans.controllers;


import com.trans.model.Cargo;
import com.trans.model.Transport;
import com.trans.model.User;
import com.trans.service.CargoService;
import com.trans.service.TransportService;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
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

    @GetMapping("/")
    protected ModelAndView listCargoAsk(ModelAndView modelAndView) {
        modelAndView.addObject("countAllCargo", cargoService.findAll().size());
        modelAndView.addObject("countAllTransport", transportService.findAll().size());
        modelAndView.addObject("countAllUser", userService.findAll().size());
        modelAndView.setViewName("pages/main");

        List<User> allUsers = userService.findAll();
        int maxTransport = allUsers.stream().map(p -> p.getTransportList().size()).max(Comparator.naturalOrder()).orElse(0);
        int maxCargo = allUsers.stream().map(p -> p.getCargoList().size()).max(Comparator.naturalOrder()).orElse(0);
        User userMaxTransport = allUsers.stream().filter(user -> user.getTransportList().size()==maxTransport).findFirst().orElse(null);
        User userMaxCargo = allUsers.stream().filter(user -> user.getCargoList().size()==maxCargo).findFirst().orElse(null);
        modelAndView.addObject("userMaxTransport",userMaxTransport);
        modelAndView.addObject("userMaxCargo",userMaxCargo);
        return modelAndView;
    }

    @GetMapping("/cargo")
    protected ModelAndView listCargoAsktest(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "Any") String cityFrom) {
        ModelAndView modelAndView = new ModelAndView();
        Set<String> uniqueCityFromCargo = cargoService.getDistinctCityFromCargo();
        Page<Cargo> cargoPage = cargoService.findAllByCityFromContaining(cityFrom, page);
        modelAndView.addObject("citiesFrom", uniqueCityFromCargo);
        modelAndView.addObject("cargoList", cargoPage.getContent());
        modelAndView.addObject("cargoPage", page);
        modelAndView.addObject("cityFrom", cityFrom);
        int totalPages = cargoPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.setViewName("pages/cargo/list_all");
        return modelAndView;
    }

    @GetMapping("/transports")
    protected ModelAndView listTransportAsk(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "All") String typeTransport) {
        ModelAndView modelAndView = new ModelAndView();
        Page<Transport> transportPage = transportService.findAllByType(typeTransport, page);
        modelAndView.addObject("transportList", transportPage.getContent());
        modelAndView.addObject("transportPage", page);
        modelAndView.addObject("typeTransport", typeTransport);
        int totalPages = transportPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.setViewName("pages/transport/list_all");
        return modelAndView;
    }
}
