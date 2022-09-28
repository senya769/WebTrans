package com.trans.controllers;

import com.trans.model.Cargo;
import com.trans.model.Transport;
import com.trans.model.User;
import com.trans.service.CargoService;
import com.trans.service.TransportService;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/user/{id}/transport")
public class TransportController {

    private final TransportService transportService;
    private final UserService userService;

    @Autowired
    public TransportController(TransportService transportService, UserService userService) {
        this.transportService = transportService;
        this.userService = userService;
    }

    @GetMapping("/list")
    protected ModelAndView list(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findById(id);
        if (user.getTransportList().isEmpty()) {
            modelAndView.addObject("notExists", true);
        } else {
            modelAndView.addObject("notExists", false);
        }
        modelAndView.addObject("user", user);
        modelAndView.setViewName("pages/transport/list_transport");
        return modelAndView;
    }

    @GetMapping("/add")
    protected ModelAndView addGet(ModelAndView modelAndView,@PathVariable int id) {
        modelAndView.addObject("user_transport_id",id);
        modelAndView.setViewName("pages/transport/add_transport");
        return modelAndView;
    }

    @PostMapping("/add")
    protected ModelAndView addPost(@ModelAttribute Transport transport, @PathVariable int id, RedirectAttributes attributes) {
        ModelAndView modelAndView = new ModelAndView();
        transport.setUser(userService.findById(id));
        transportService.save(transport);
        modelAndView.addObject("isCreateTransport", true);
        //        attributes.addAttribute("id", id);
        modelAndView.setViewName("pages/transport/success_add_transport");
        return modelAndView;
    }
    @GetMapping("/remove/{transport}")
    protected ModelAndView removeCargo(@PathVariable("id") int id_user,@PathVariable int id_transport){
        ModelAndView modelAndView = new ModelAndView();
        if(transportService.deleteTransportById(id_transport)){
            modelAndView.addObject("isDelete",true);
            modelAndView.setViewName("forward:/user/profile/" + id_user);
        }
        else {
            modelAndView.addObject("isDelete",false);
            modelAndView.setViewName("redirect:/error");
        }
        return modelAndView;
    }

    @ModelAttribute("transport")
    public Transport newCargo() {
        return new Transport();
    }
}
