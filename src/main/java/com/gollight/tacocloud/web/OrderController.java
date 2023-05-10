package com.gollight.tacocloud.web;

import javax.validation.Valid;
import org.springframework.validation.Errors;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.gollight.tacocloud.TacoOrder;
import com.gollight.tacocloud.data.api.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/orders")
@Controller
@SessionAttributes("tacoOrder")
public class OrderController {
    private OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
        
        if (errors.hasErrors()) {
            return "orderForm";
          }
        orderRepository.save(order);
        log.info("processOrder submited {}" + order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
    
}
