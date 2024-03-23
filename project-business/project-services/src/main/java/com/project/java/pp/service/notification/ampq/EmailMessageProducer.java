package com.project.java.pp.service.notification.ampq;

import com.project.java.pp.service.notification.Notifier;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;

import java.util.Map;

@RequiredArgsConstructor
public class EmailMessageProducer implements Notifier {
    private final RabbitTemplate rabbitTemplate;
    private final Binding binding;
    private final MessageConverter converter;


    @Override
    public void sendMessage(String email, String name) {
        MessageProperties properties = new MessageProperties();
        properties.setHeaders(Map.of("email",email,"name",name));
        rabbitTemplate.convertAndSend(binding.getExchange(),binding.getRoutingKey(),converter.toMessage("",properties));
    }
}
