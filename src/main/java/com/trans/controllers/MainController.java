package com.trans.controllers;


import com.trans.model.Cargo;
import com.trans.model.Transport;
import com.trans.model.enums.TypeTransport;
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
        modelAndView.addObject("countAllCargo", cargoService.findAllByDeleteIsFalseAndFreeIsTrue().size());
        modelAndView.addObject("countAllTransport", transportService.findAllByDeleteIsFalseAndFreeIsTrue().size());
        modelAndView.addObject("countAllUser", userService.findAll().size());
        modelAndView.setViewName("pages/main");
        return modelAndView;
    }

    @GetMapping("/cargo")
    protected ModelAndView listCargoAsk(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String countryFrom,
                                        @RequestParam(required = false) String countryTo,
                                        @RequestParam(required = false) Double price,
                                        @RequestParam(required = false) Double volume,
                                        @RequestParam(required = false) Double weight,
                                        @RequestParam(required = false) String cityFrom,
                                        @RequestParam(required = false) String cityTo) {
        ModelAndView modelAndView = new ModelAndView();
        Page<Cargo> list = cargoService.searchByArgs(page,keyword, countryFrom, countryTo,cityFrom, cityTo,price,volume,weight);
        int totalPages = list.getTotalPages();
        List<Cargo> cargoPage = list.stream().filter(Cargo -> !Cargo.isDelete() && Cargo.isFree()).toList();

        modelAndView.addObject("cargoList", cargoPage);
        modelAndView.addObject("cargoPage", page);
        modelAndView.addObject("keywordPage", keyword);
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
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = false) Double maxCapacityLoad,
                                            @RequestParam(required = false) TypeTransport type
                                            ) {
        Page<Transport> list = transportService.searchByArgs(page,name,type,maxCapacityLoad);
        int totalPages = list.getTotalPages();
        List<Transport> transportPage = list.stream().filter(transport -> !transport.isDelete() && transport.isFree()).toList();
        modelAndView.addObject("transportList", transportPage);
        modelAndView.addObject("transportPage", page);
        modelAndView.addObject("keywordPage", name);

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
