package com.trans.controllers;

import com.trans.service.CargoService;
import com.trans.service.OrderService;
import com.trans.service.TransportService;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView getTransportReceivedOrders(ModelAndView modelAndView, @PathVariable Integer user_id) {
        modelAndView.addObject("orders",orderService.getTransportReceivedOrdersById(user_id));
        modelAndView.setViewName("pages/orders");
        return modelAndView;
    }

    @GetMapping("/cargo/received/orders")
    public ModelAndView getCargoReceivedOrders(ModelAndView modelAndView, @PathVariable Integer user_id) {
        modelAndView.addObject("orders",orderService.getCargoReceivedOrdersById(user_id));
        modelAndView.setViewName("pages/orders");
        return modelAndView;
    }

    @GetMapping("/transports/sent/orders")
    public ModelAndView getTransportSentOrders(ModelAndView modelAndView, @PathVariable Integer user_id) {
        modelAndView.addObject("orders",orderService.getTransportSentOrdersById(user_id));
        modelAndView.setViewName("pages/orders");
        return modelAndView;
    }

    @GetMapping("/cargo/sent/orders")
    public ModelAndView getCargoSentOrders(ModelAndView modelAndView, @PathVariable Integer user_id) {
        modelAndView.addObject("orders",orderService.getCargoSentOrdersById(user_id));
        modelAndView.setViewName("pages/orders");
        return modelAndView;
    }

  /*  @GetMapping("/transport/order")
    public ModelAndView getTransportOrder(ModelAndView modelAndView, @PathVariable Integer id){
        return modelAndView;
    }
    @GetMapping("/cargo/order")
    public ModelAndView getCargoOrder(ModelAndView modelAndView, @PathVariable Integer id){
        return modelAndView;
    }*/
}
