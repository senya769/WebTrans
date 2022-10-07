package com.trans.controllers;

import com.trans.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user/{id}/transport/")
public class OrderTransportController {
    private final OrderService orderService;

    @Autowired
    public OrderTransportController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping("/orders")
    public ModelAndView ordersList(@PathVariable("id") int user_id,ModelAndView modelAndView){
        modelAndView.addObject("transportOrdersList",orderService.findAllByTransportUserId(user_id));
        modelAndView.setViewName("/pages/about");
        return modelAndView;
    }

}
