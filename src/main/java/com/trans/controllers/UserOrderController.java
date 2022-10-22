package com.trans.controllers;

import com.trans.model.Order;
import com.trans.model.util.CustomUserDetails;
import com.trans.service.CargoService;
import com.trans.service.OrderService;
import com.trans.service.TransportService;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/users/{user_id}/")
public class UserOrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final CargoService cargoService;
    private final TransportService transportService;

    @Autowired
    public UserOrderController(OrderService orderService, UserService userService, CargoService cargoService, TransportService transportService) {
        this.orderService = orderService;
        this.userService = userService;
        this.cargoService = cargoService;
        this.transportService = transportService;
    }

    @GetMapping("/transports/received/orders")
    public ModelAndView getTransportReceivedOrders(ModelAndView modelAndView, @PathVariable Integer user_id,
                                                   @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails.getId() == user_id) {
            modelAndView.addObject("orders", orderService.getTransportReceivedOrdersById(user_id));
            modelAndView.setViewName("pages/order/transport_received");
        } else {
            modelAndView.addObject("id", user_id);
            modelAndView.setViewName("pages/error/405");
        }
        return modelAndView;
    }

    @GetMapping("/cargo/received/orders")
    public ModelAndView getCargoReceivedOrders(ModelAndView modelAndView, @PathVariable Integer user_id,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<Order> cargoOrders = orderService.getCargoReceivedOrdersById(user_id);
        if (userDetails.getId() == user_id) {
            modelAndView.addObject("orders", cargoOrders);
            modelAndView.setViewName("pages/order/cargo_received");
        }else {
            modelAndView.addObject("id", user_id);
            modelAndView.setViewName("pages/error/405");
        }
        return modelAndView;
    }

    @GetMapping("/transports/sent/orders")
    public ModelAndView getTransportSentOrders(ModelAndView modelAndView, @PathVariable Integer user_id,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails.getId() == user_id) {
            modelAndView.addObject("orders", orderService.getTransportSentOrdersById(user_id));
            modelAndView.setViewName("pages/order/transport_sent");
        }else {
            modelAndView.addObject("id", user_id);
            modelAndView.setViewName("pages/error/405");
        }
        return modelAndView;
    }

    @GetMapping("/cargo/sent/orders")
    public ModelAndView getCargoSentOrders(ModelAndView modelAndView, @PathVariable Integer user_id,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails.getId() == user_id) {
            modelAndView.addObject("orders", orderService.getCargoSentOrdersById(user_id));
            modelAndView.setViewName("pages/order/cargo_sent");
        }else {
            modelAndView.addObject("id", user_id);
            modelAndView.setViewName("pages/error/405");
        }
        return modelAndView;
    }
}
