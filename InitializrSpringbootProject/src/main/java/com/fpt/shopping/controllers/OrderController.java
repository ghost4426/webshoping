package com.fpt.shopping.controllers;

import com.fpt.shopping.entities.Order;
import com.fpt.shopping.entities.OrderDetail;
import com.fpt.shopping.entities.User;
import com.fpt.shopping.repositories.OrderDetailRepository;
import com.fpt.shopping.repositories.OrderRepository;
import com.fpt.shopping.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    Iterable<Order> readAll() {
        return orderRepository.findAll();
    }

    @GetMapping("/my")
    Iterable<Order> readMy() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal).getUsername();
        } else {
             username = principal.toString();
        }
        User user = userRepository.findByName(username).get();
        return orderRepository.getByName(user.getId());
    }

    @GetMapping("/{id}")
    Order read(@PathVariable int id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    @GetMapping("/{id}/view")
    Iterable<OrderDetail> readDetail(@PathVariable int id) {
        return orderDetailRepository.search(id);
    }

    @PostMapping("")
    Order create(@RequestBody Order newOrder) {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userRepository.findByName(username).get();
        newOrder.setUserId(user.getId());
        newOrder.setOrderDate(Calendar.getInstance().getTime());
        newOrder.setModified(Calendar.getInstance().getTime());
        return orderRepository.save(newOrder);
    }

    @PostMapping("/{id}/shipped")
    Order shipped(@PathVariable int id) {
        Order shipped = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException (Integer.toString(id)));
        shipped.setShipped(true);
        shipped.setModified(Calendar.getInstance().getTime());

        return orderRepository.save(shipped);
    }

    @PutMapping("/{id}")
    Order update(@RequestBody Order updatingOrder, @PathVariable int id) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setDescription(updatingOrder.getDescription());
                    order.setShipped(updatingOrder.isShipped());
                    order.setUserId(updatingOrder.getUserId());
                    order.setValid(updatingOrder.isValid());
                    order.setModified(Calendar.getInstance().getTime());

                    return orderRepository.save(order);
                })
                .orElseGet(() -> {
                    updatingOrder.setId(id);
                    return orderRepository.save(updatingOrder);
                });
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        orderRepository.deleteById(id);
    }
}
