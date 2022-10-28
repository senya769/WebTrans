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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrderController {
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
    public ModelAndView model(ModelAndView modelAndView, @PathVariable int cargo_id,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        Cargo cargo = cargoService.findById(cargo_id);
        modelAndView.addObject("cargo", cargo);
       // List<Transport> transportListOfCustomer = userService.findById(userDetails.getId()).getTransportList();
        List<Transport> transportListOfCustomer = userService.findById(userDetails.getId()).getTransportList().stream()
                .filter(ts -> ts.getType() == cargo.getTypeTransport()).toList();
        if (transportListOfCustomer == null) {
            modelAndView.addObject("NotFoundTransportOfCustomer", true);
            modelAndView.setViewName("redirect:/cargo/list");
        } else {
            modelAndView.addObject("transportListCustomer", transportListOfCustomer);
            modelAndView.setViewName("pages/transport/send");
        }
        return modelAndView;
    }

    @PostMapping("/cargo/{cargo_id}/book")
    public ModelAndView model(ModelAndView modelAndView, @RequestParam Integer transport_id,
                              @PathVariable Integer cargo_id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Order order = new Order();
        order.setTransport(transportService.findById(transport_id));
        order.setCargo(cargoService.findById(cargo_id));
        order.setCustomerId(userDetails.getId());
        modelAndView.addObject("cargoOwner", cargoService.findById(cargo_id));
        modelAndView.addObject("orderBuild", order);
        if (orderService.save(order) != 0) {
            modelAndView.setViewName("pages/about");
        } else {
            modelAndView.setViewName("pages/error/400");
        }
        return modelAndView;
    }

    @GetMapping("/transports/{ts_id}/book")
    public ModelAndView bookCargo(ModelAndView modelAndView, @PathVariable int ts_id,
                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        Transport transport = transportService.findById(ts_id);
        modelAndView.addObject("transport", transport);
        List<Cargo> cargoListOfCustomer = userService.findById(userDetails.getId()).getCargoList();
        if (cargoListOfCustomer == null) {
            modelAndView.addObject("NotFoundCargoOfCustomer", true);
            modelAndView.setViewName("redirect:/transports");
        } else {
            modelAndView.addObject("cargoListCustomer", cargoListOfCustomer);
            modelAndView.setViewName("pages/cargo/send");
        }
        return modelAndView;
    }

    @PostMapping("/transports/{cargo_id}/book")
    public ModelAndView bookCargo(ModelAndView modelAndView, @RequestParam Integer transport_id,
                                  @PathVariable Integer cargo_id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Order order = Order.builder()
                .transport(transportService.findById(transport_id))
                .cargo(cargoService.findById(cargo_id))
                .customerId(userDetails.getId())
                .status(OrderStatus.WAITING)
                .build();
        modelAndView.addObject("cargoOwner", cargoService.findById(cargo_id));
        modelAndView.addObject("orderBuild", order);
        if (orderService.save(order) != 0) {
            modelAndView.setViewName("pages/about");
        } else {
            modelAndView.setViewName("pages/error/400");
        }

        return modelAndView;
    }

    @GetMapping("/{order_id}/accept")
    public ModelAndView acceptOrder(ModelAndView modelAndView, @PathVariable Integer order_id, RedirectAttributes attributes,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        Order order = orderService.findById(order_id);
        if (orderService.accept(order) != 0) {
            attributes.addFlashAttribute("acceptOrder", true);
            modelAndView.setViewName("redirect:/users/" + userDetails.getId() + "/profile");
        } else {
            modelAndView.addObject("");
            modelAndView.setViewName("pages/error/400");
        }
        return modelAndView;
    }

    @GetMapping("/{order_id}/cancel")
    public ModelAndView cancelOrder(ModelAndView modelAndView, @PathVariable Integer order_id,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        Order order = orderService.findById(order_id);
        if (orderService.cancel(order) != 0) {
            modelAndView.addObject("");
            modelAndView.setViewName("redirect:/users/" + userDetails.getId() + "/profile");


        } else {
            modelAndView.addObject("");
            modelAndView.setViewName("pages/error/400");
        }
        return modelAndView;
    }

    @GetMapping("/{order_id}/complete")
    public ModelAndView completeOrder(ModelAndView modelAndView, @PathVariable Integer order_id,
                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        Order order = orderService.findById(order_id);
        if (orderService.complete(order) != 0) {
            modelAndView.addObject("");
            modelAndView.setViewName("redirect:/users/" + userDetails.getId() + "/profile");


        } else {
            modelAndView.addObject("");
            modelAndView.setViewName("pages/error/400");
        }
        return modelAndView;
    }

    @ModelAttribute("order")
    public Order order() {
        return new Order();
    }
}
