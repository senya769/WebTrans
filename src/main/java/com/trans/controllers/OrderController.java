package com.trans.controllers;

import com.trans.model.Cargo;
import com.trans.model.Order;
import com.trans.model.Transport;
import com.trans.model.enums.OrderStatus;
import com.trans.model.util.CustomUserDetails;
import com.trans.service.CargoService;
import com.trans.service.OrderService;
import com.trans.service.TransportService;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController{
    private final OrderService orderService;
    private final UserService userService;
    private final CargoService cargoService;
    private final TransportService transportService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, CargoService cargoService, TransportService transportService) {
        this.orderService = orderService;
        this.userService = userService;
        this.cargoService = cargoService;
        this.transportService = transportService;
    }

    /*    @GetMapping("/orders")
        public ModelAndView getOrderList(ModelAndView modelAndView, @PathVariable int user_id){
            modelAndView.addObject("listOrdersCargo",orderService.findAllByCargoUserId(user_id));
            modelAndView.setViewName("/pages/about");
            return modelAndView;
        }*/
    @GetMapping("/cargo/{cargo_id}/book")
    public ModelAndView model(ModelAndView modelAndView, @PathVariable int cargo_id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Cargo cargo = cargoService.findById(cargo_id);
        modelAndView.addObject("cargo", cargo);
        List<Transport> transportListOfCustomer = userService.findById(userDetails.getId()).getTransportList();
        if (transportListOfCustomer == null) {
            modelAndView.addObject("NotFoundTransportOfCustomer", true);
            modelAndView.setViewName("redirect:/cargo/list");
        } else {
            modelAndView.addObject("transportListCustomer", transportListOfCustomer);
            modelAndView.setViewName("pages/test");
        }
        return modelAndView;
    }

    @PostMapping("/cargo/{cargo_id}/book")
    public ModelAndView model(ModelAndView modelAndView, @RequestParam Integer transport_id,
                              @PathVariable Integer cargo_id, @RequestParam Integer customer_id) {
        Order order = new Order();
        order.setTransport(transportService.findById(transport_id));
        order.setCargo(cargoService.findById(cargo_id));
        order.setCustomerId(customer_id);
        modelAndView.addObject("cargoOwner", cargoService.findById(cargo_id));
        modelAndView.addObject("orderBuild", order);
        if (orderService.save(order) != 0) {
            modelAndView.setViewName("pages/about");
        }else {
            modelAndView.setViewName("/error");
        }

        return modelAndView;
    }

    @PostMapping("/accept/{order_id}/")
    public ModelAndView acceptOrder(ModelAndView modelAndView, @PathVariable Integer order_id){
        Order order = orderService.findById(order_id);
        if(orderService.accept(order) != 0){
            modelAndView.addObject("");
            modelAndView.setViewName("");
        }else{
            modelAndView.addObject("");
            modelAndView.setViewName("");
        }
        return modelAndView;
    }
    @PostMapping("/cancel/{order_id}/")
    public ModelAndView cancelOrder(ModelAndView modelAndView, @PathVariable Integer order_id){
        Order order = orderService.findById(order_id);
        if(orderService.cancel(order) != 0){
            modelAndView.addObject("");
            modelAndView.setViewName("");
        }else{
            modelAndView.addObject("");
            modelAndView.setViewName("");
        }
        return modelAndView;
    }
    @PostMapping("/complete/{order_id}/")
    public ModelAndView completeOrder(ModelAndView modelAndView, @PathVariable Integer order_id){
        Order order = orderService.findById(order_id);
        if(orderService.complete(order) != 0){
            modelAndView.addObject("");
            modelAndView.setViewName("");
        }else{
            modelAndView.addObject("");
            modelAndView.setViewName("");
        }
        return modelAndView;
    }
    @ModelAttribute("order")
    public Order order() {
        return new Order();
    }
}
