package com.trans.controllers;


import com.trans.model.Cargo;
import com.trans.model.Transport;
import com.trans.model.User;
import com.trans.service.CargoService;
import com.trans.service.TransportService;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        modelAndView.addObject("countAllCargo", cargoService.findAllByDeleteIsFalseAndFreeIsTrue().size());
        modelAndView.addObject("countAllTransport", transportService.findAllByDeleteIsFalseAndFreeIsTrue().size());
        modelAndView.addObject("countAllUser", userService.findAll().size());
        modelAndView.setViewName("pages/main");
        return modelAndView;
    }

    @GetMapping("/cargo")
    protected ModelAndView listCargoAsktest(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(required = false) String keyword) {
        ModelAndView modelAndView = new ModelAndView();
        Page<Cargo> cargoPage = cargoService.searchAllByKeyword(keyword, page);
        modelAndView.addObject("cargoList", cargoPage.getContent());
        modelAndView.addObject("cargoPage", page);
        modelAndView.addObject("keywordPage", keyword);
        int totalPages = cargoPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.setViewName("pages/cargo/list_all");
        return modelAndView;
    }

    @GetMapping("/transports")
    protected ModelAndView listTransportAsk(ModelAndView modelAndView,
                                            @RequestParam(defaultValue = "1") int page,
                                            @RequestParam(required = false) String keyword) {
        Page<Transport> transportPage = transportService.search(keyword,page);
        modelAndView.addObject("transportList", transportPage.getContent());
        modelAndView.addObject("transportPage", page);
        modelAndView.addObject("keywordPage", keyword);

        int totalPages = transportPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.setViewName("pages/transport/list_all");
        return modelAndView;
    }
    @ModelAttribute("cargo")
    public Cargo newCargo(){
        return new Cargo();
    }
}
