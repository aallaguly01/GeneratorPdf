package ru.itis.pdfgenerator.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {
    public static final String RPC_QUEUE1 = "queue_1";
    public static final String RPC_QUEUE2 = "queue_2";
    public static final String RPC_EXCHANGE = "rpc_exchange";

    @Bean
    Queue msgQueue() {
        return new Queue(RPC_QUEUE1);
    }

    @Bean
    Queue replyQueue() {
        return new Queue(RPC_QUEUE2);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(RPC_EXCHANGE);
    }

    @Bean
    Binding msgBinding() {
        return BindingBuilder.bind(msgQueue()).to(exchange()).with(RPC_QUEUE1);
    }

    @Bean
    Binding replyBinding() {
        return BindingBuilder.bind(replyQueue()).to(exchange()).with(RPC_QUEUE2);
    }


}
