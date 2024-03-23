package com.project.java.pp.service.config;

import com.project.java.pp.service.notification.EmailGenerator;
import com.project.java.pp.service.notification.ampq.EmailMessageConsumer;
import com.project.java.pp.service.notification.ampq.EmailMessageProducer;
import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;


@Data
@ConfigurationProperties(prefix = "rabbitmq") // Замена Value для трех параметров конфига, т.е из propeties берутся значения с префиксом rabbitmq
@ConditionalOnProperty(prefix = "rabbitmq", name = "use-queue", havingValue = "true") // Условие выполнения : если пропери с именем rabbitmq.use-queue true то интерфейс Notifier создаст бин через EmailGenerator, если нет то через SimpleNotifier(Без использования rabbtimq)
public class RabbitMQConfig {
    private String queueName;
    private String exchangeName;
    private String routingKey;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchangeName, false, false);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder
                .nonDurable(queueName)
                .build();
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange) {
        return BindingBuilder
                .bind(queue)
                .to(directExchange)
                .with(routingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public EmailMessageProducer emailMessageProducer(RabbitTemplate rabbitTemplate, Binding binding, MessageConverter messageConverter) {
        return new EmailMessageProducer(rabbitTemplate, binding, messageConverter);
    }

    @Bean
    public EmailMessageConsumer emailMessageConsumer(EmailGenerator emailGenerator, JavaMailSender mailSender) {
        return new EmailMessageConsumer(emailGenerator, mailSender);
    }

    @Bean
    public String queueName() {
        return queueName;
    }

}
