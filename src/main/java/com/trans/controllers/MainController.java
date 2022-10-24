package com.trans.controllers;


import com.trans.model.Cargo;
import com.trans.model.Transport;
import com.trans.service.CargoService;
import com.trans.service.TransportService;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;
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
        modelAndView.addObject("countAllTransport",transportService.findAll().size());
        modelAndView.setViewName("pages/main");
        return modelAndView;
    }

    @GetMapping("/cargo")
    protected ModelAndView listCargoAskPost(@RequestParam(defaultValue = "1") int page) {
        ModelAndView modelAndView = new ModelAndView();
        Page<Cargo> cargoPage = cargoService.findAllSortByDateCreated(page);
        Set<String> citiesFromValues = cargoService.findAll().stream().map(Cargo::getCityFrom).collect(Collectors.toSet());
        modelAndView.addObject("citiesFrom",citiesFromValues);
        modelAndView.addObject("cargoList", cargoPage.getContent());
        modelAndView.addObject("cargoPage", cargoPage.getNumber()+1);
        int totalPages = cargoPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.setViewName("pages/cargo/list_all");
        return modelAndView;
    }
    @GetMapping("/cargo/list1")
    protected ModelAndView listCargoAsktest(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "") String cityFrom) {
        ModelAndView modelAndView = new ModelAndView();
        Set<String> citiesFromValues = cargoService.findAll().stream().map(Cargo::getCityFrom).collect(Collectors.toSet());
        modelAndView.addObject("citiesFrom",citiesFromValues);
        modelAndView.addObject("cargoList",cargoService.findAllByCityFromContaining(cityFrom));
//        Page<Cargo> cargoPage = cargoService.findAllSortByDateCreated(page);
       /* modelAndView.addObject("cargoList", cargoPage.getContent());
        modelAndView.addObject("cargoPage", cargoPage.getNumber()+1);*/
       /* int totalPages = cargoPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }*/
        modelAndView.setViewName("pages/cargo/list_all");
        return modelAndView;
    }

    @GetMapping("/test/{ts}")
    protected ModelAndView listTransportAsk(@PathVariable Integer ts) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("transport",new Transport());
        modelAndView.addObject("id",ts);
        modelAndView.setViewName("pages/transport/add-test");
        return modelAndView;
    }

    @PostMapping("/test/{ts}")
    protected ModelAndView listTransportAsk(@ModelAttribute Transport transport,@PathVariable Integer ts) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ts",transport);
        modelAndView.setViewName("pages/transport/add-test");
        return modelAndView;
    }
    @GetMapping("/test")
    protected ModelAndView list1TransportAsk1(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("transport",new Transport());
        modelAndView.setViewName("pages/transport/add-test");
        return modelAndView;
    }

    @PostMapping("/test")
    protected ModelAndView list2TransportAsk(@ModelAttribute Transport transport) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ts",transport);
        modelAndView.setViewName("pages/transport/add-test");
        return modelAndView;
    }

    @GetMapping("/transports")
    protected ModelAndView listTransportAsk(@RequestParam(defaultValue = "1") int page) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("transportList", transportService.findAll());
        modelAndView.setViewName("pages/transport/list_all");
        return modelAndView;
    }

    @GetMapping("/testSet")
    public ModelAndView test131(ModelAndView modelAndView){
        Set<String> citiesFromValues = cargoService.findAll().stream().map(Cargo::getCityFrom).collect(Collectors.toSet());
        modelAndView.setViewName("pages/test");
        modelAndView.addObject("citiesFrom",citiesFromValues);
        return modelAndView;
    }
}
