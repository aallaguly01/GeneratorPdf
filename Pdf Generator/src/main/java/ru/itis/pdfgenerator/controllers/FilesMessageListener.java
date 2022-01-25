package ru.itis.pdfgenerator.controllers;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.pdfgenerator.config.RabbitConfig;
import ru.itis.pdfgenerator.services.GeneratePDF;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Component
public class FilesMessageListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private GeneratePDF pdfGenerator;

    @RabbitListener(queues = RabbitConfig.RPC_QUEUE1)
    public void process(Message msg) {

        Message response = MessageBuilder.withBody((new String(msg.getBody())).getBytes()).build();
        CorrelationData correlationData = new CorrelationData(msg.getMessageProperties().getCorrelationId());

        String path = pdfGenerator.generate(new String(response.getBody()));

        Path pdfPath = Paths.get(path);
        byte[] con = new byte[0];

        try {
            con = Files.readAllBytes(pdfPath);
        } catch (IOException e) {
            System.err.println("I can not read");
        }

        Message newMessage = new Message(con);

        System.err.println(newMessage);

        rabbitTemplate.sendAndReceive(RabbitConfig.RPC_EXCHANGE, RabbitConfig.RPC_QUEUE2, newMessage, correlationData);
    }
}

