package com.trans.controllers;

import com.trans.dto.UserDTO;
import com.trans.model.Transport;
import com.trans.model.util.CustomUserDetails;
import com.trans.service.TransportService;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    protected RedirectView addPost(RedirectAttributes attributes,
            @ModelAttribute Transport transport, @PathVariable int user_id) {
        transportService.saveWithUser(transport, userService.findById(user_id));
        attributes.addFlashAttribute("isCreateTransport", true);
        attributes.addAttribute("user_id", user_id);
        //кидать уведомление на гл
        return new RedirectView("/users/{user_id}/profile",true);
    }

    @GetMapping("/remove/{transport}")
    protected RedirectView removeCargo(RedirectAttributes redirectAttributes,@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @PathVariable("user_id") int user_id, @PathVariable("transport") int transport_id) {
        redirectAttributes.addAttribute("user_id",user_id);
        if (transportService.deleteById(transport_id)&& userDetails.getId() == user_id) {
            redirectAttributes.addFlashAttribute("transportIsDelete", true);
        } else {
            redirectAttributes.addFlashAttribute("isNotFoundTransport", true);
        }
        return new RedirectView("/users/{user_id}/profile",true);

    }

    @ModelAttribute("transport")
    public Transport ts() {
        return new Transport();
    }
}
