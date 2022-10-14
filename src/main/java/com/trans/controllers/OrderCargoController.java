package com.trans.controllers;

import com.trans.model.Cargo;
import com.trans.model.Order;
import com.trans.model.Transport;
import com.trans.service.CargoService;
import com.trans.service.OrderService;
import com.trans.service.TransportService;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/order/cargo/{cargo_id}")
public class OrderCargoController {
    private final OrderService orderService;
    private final UserService userService;
    private final CargoService cargoService;
    private final TransportService transportService;

    @Autowired
    public OrderCargoController(OrderService orderService, UserService userService, CargoService cargoService, TransportService transportService) {
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
    @GetMapping("/book")
    public ModelAndView model(ModelAndView modelAndView, @PathVariable int cargo_id, @RequestParam int customer_id) {
        Cargo cargo = cargoService.findById(cargo_id);
        modelAndView.addObject("cargo", cargo);
        List<Transport> transportListOfCustomer = userService.findById(customer_id).getTransportList();
        if (transportListOfCustomer == null) {
            modelAndView.addObject("NotFoundTransportOfCustomer", true);
            modelAndView.setViewName("redirect:/cargo/list");
        } else {
            modelAndView.addObject("transportListCustomer", transportListOfCustomer);
            modelAndView.setViewName("pages/test");
        }
        return modelAndView;
    }

    @PostMapping("/book")
    public ModelAndView model(ModelAndView modelAndView, @RequestParam Integer transport,
                              @PathVariable Integer cargo_id, @RequestParam Integer customer_id) {
        Order order = new Order();
        order.setTransport(transportService.findById(transport));
        order.setCargo(cargoService.findById(cargo_id));
        modelAndView.addObject("cargoOwner", cargoService.findById(cargo_id));
        modelAndView.addObject("orderBuild", order);
        if (orderService.save(order) != 0) {
            modelAndView.setViewName("pages/about");
        }else {
            modelAndView.setViewName("/error");
        }

        return modelAndView;
    }

    @ModelAttribute("order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute("cargoOwner")
    public Cargo cargo() {
        return new Cargo();
    }

    @ModelAttribute("transportCustomer")
    public Transport transport() {
        return new Transport();
    }

}
