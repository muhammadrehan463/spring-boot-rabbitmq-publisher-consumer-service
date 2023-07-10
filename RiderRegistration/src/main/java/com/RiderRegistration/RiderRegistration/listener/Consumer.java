package com.RiderRegistration.RiderRegistration.listener;

import com.RiderRegistration.RiderRegistration.entity.OrderStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @RabbitListener(queues = "order_queue")
    public void consumeMessageFromQueue(OrderStatus orderStatus) {
        System.out.println("Message recieved: " + orderStatus);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}
