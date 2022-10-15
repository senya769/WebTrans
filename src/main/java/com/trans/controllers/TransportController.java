package com.trans.controllers;

import com.trans.dto.UserDTO;
import com.trans.model.Transport;
import com.trans.service.TransportService;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/users/{user_id}/transports")
public class TransportController {

    private final TransportService transportService;
    private final UserService userService;

    @Autowired
    public TransportController(TransportService transportService, UserService userService) {
        this.transportService = transportService;
        this.userService = userService;
    }

    @GetMapping()
    protected ModelAndView list(@PathVariable int user_id, ModelAndView modelAndView) {
        UserDTO user = userService.findDTOById(user_id);
        if (user.getTransportList().isEmpty()) {
            modelAndView.addObject("notExists", true);
        } else {
            modelAndView.addObject("notExists", false);
        }
        modelAndView.addObject("user", user);
        modelAndView.setViewName("pages/transport/list");
        return modelAndView;
    }

    @GetMapping("/add")
    protected ModelAndView addGet(ModelAndView modelAndView, @PathVariable int user_id) {
        modelAndView.addObject("user_id", user_id);
        modelAndView.addObject("tr",new Transport());
        modelAndView.setViewName("pages/transport/add");
        return modelAndView;
    }


    @PostMapping("/add")
    protected ModelAndView addPost(@ModelAttribute Transport transport, @PathVariable int user_id) {
        ModelAndView modelAndView = new ModelAndView();
        transportService.saveWithUser(transport, userService.findById(user_id));
        modelAndView.addObject("isCreateTransport", true);
        //кидать уведомление на гл
        modelAndView.setViewName("pages/transport/success_add");
        return modelAndView;
    }

    @GetMapping("/remove/{transport}")
    protected RedirectView removeCargo(RedirectAttributes redirectAttributes,
                                       @PathVariable("user_id") int id_user, @PathVariable("transport") int transport_id) {
        if (transportService.deleteById(transport_id)) {
            redirectAttributes.addFlashAttribute("transportIsDelete", true);
        } else {
            redirectAttributes.addFlashAttribute("isNotFoundTransport", true);
        }
        return new RedirectView("/users/profile/" + id_user,true);
    }

    @ModelAttribute("transport")
    public Transport ts() {
        return new Transport();
    }
}
