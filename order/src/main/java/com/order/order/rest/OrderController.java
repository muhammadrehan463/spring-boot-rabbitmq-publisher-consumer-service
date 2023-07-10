package com.order.order.rest;

import com.order.order.config.MessagingConfig;
import com.order.order.entity.Order;
import com.order.order.entity.OrderStatus;
import com.order.order.service.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private OrderService orderService;

    private RabbitTemplate template;

    @Autowired
    public OrderController(OrderService theOrderService, RabbitTemplate theTemplate) {
        orderService = theOrderService;
        template = theTemplate;
    }

    // !======! Get All Order Data !======! //

    @GetMapping("/get-orders")
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    // !======! Get Order Data By Id !======! //

    @GetMapping("/get-orderbyid/{orderId}")
    public Order getOrderById(@PathVariable int orderId) {
        Order theOrder = orderService.findById(orderId);
        if (theOrder == null) {
            throw new RuntimeException("Order with id: " + orderId + " not found");
        }

        return theOrder;
    }

    // !======! Create Order !======! //

    @PostMapping("/create-order")
    public String addOrder(@RequestBody Order theOrder) {
        orderService.save(theOrder);

        // Sending message to the queue.
        OrderStatus orderStatus = new OrderStatus(theOrder, " PROCESS,", " Order is in process");
        template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, orderStatus);
        return "Order placed and message sent to the rider.";
    }

    // !======! Update Order Data !======! //

    @PutMapping("/update-order")
    public Order updateOrder(@RequestBody Order theOrder) {
        return orderService.save(theOrder);
    }

    // !======! Delete Order Data !======! //

    @DeleteMapping("/delete-order/{orderId}")
    public String deleteOrder(@PathVariable int orderId) {
        Order tempOrder = orderService.findById(orderId);

        if (tempOrder == null) {
            throw new RuntimeException("Order with Id: " + orderId + "not found");
        }
        orderService.delete(orderId);

        return "Order with id: " + orderId + " deleted";
    }
}
