package com.trans.controllers;

import com.trans.model.Order;
import com.trans.model.util.CustomUserDetails;
import com.trans.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/users/{user_id}/")
public class UserOrderController {
    private final OrderService orderService;


    @Autowired
    public UserOrderController(OrderService orderService) {
        this.orderService = orderService;

    }

    @GetMapping("/transports/received/orders")
    public ModelAndView getTransportReceivedOrders(ModelAndView modelAndView,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   @PathVariable Integer user_id,
                                                   @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails.getId() == user_id) {
            modelAndView.addObject("orders", orderService.getTransportReceivedOrdersById(user_id,page));
            modelAndView.setViewName("pages/order/transport_received");
        } else {
            modelAndView.addObject("id", user_id);
            modelAndView.setViewName("pages/error/405");
        }
        return modelAndView;
    }

    @GetMapping("/cargo/received/orders")
    public ModelAndView getCargoReceivedOrders(ModelAndView modelAndView,
                                               @RequestParam(defaultValue = "1") int page,
                                               @PathVariable Integer user_id,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<Order> cargoOrders = orderService.getCargoReceivedOrdersById(user_id,page).getContent();
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
    public ModelAndView getTransportSentOrders(ModelAndView modelAndView,
                                               @RequestParam(defaultValue = "1") int page,
                                               @PathVariable Integer user_id,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails.getId() == user_id) {
            modelAndView.addObject("orders", orderService.getTransportSentOrdersById(user_id,page).getContent());
            modelAndView.setViewName("pages/order/transport_sent");
        }else {
            modelAndView.addObject("id", user_id);
            modelAndView.setViewName("pages/error/405");
        }
        return modelAndView;
    }

    @GetMapping("/cargo/sent/orders")
    public ModelAndView getCargoSentOrders(ModelAndView modelAndView,
                                           @RequestParam(defaultValue = "1") int page,
                                           @PathVariable Integer user_id,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails.getId() == user_id) {
            modelAndView.addObject("orders", orderService.getCargoSentOrdersById(user_id,page));
            modelAndView.setViewName("pages/order/cargo_sent");
        }else {
            modelAndView.addObject("id", user_id);
            modelAndView.setViewName("pages/error/405");
        }
        return modelAndView;
    }
    @GetMapping("/transports/logs/orders")
    public ModelAndView getLogsOfTransports(ModelAndView modelAndView,
                                            @RequestParam(defaultValue = "1") int page,
                                            @PathVariable Integer user_id){
        modelAndView.addObject("logsOfTransports",orderService.findByTransportForLoggerInfo(user_id,page));
        modelAndView.setViewName("/pages/order/transport_logger");
        return modelAndView;
    }
    @GetMapping("/cargo/logs/orders")
    public ModelAndView getLogsOfTCargo(ModelAndView modelAndView,
                                            @RequestParam(defaultValue = "1") int page,
                                            @PathVariable Integer user_id){
        modelAndView.addObject("logsOfCargo",orderService.findByCargoForLoggerInfo(user_id,page));
        modelAndView.setViewName("/pages/order/cargo_logger");
        return modelAndView;
    }
}
