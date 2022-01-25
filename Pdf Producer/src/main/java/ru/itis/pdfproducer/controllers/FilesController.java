package ru.itis.pdfproducer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itis.pdfproducer.config.RabbitConfig;
import ru.itis.pdfproducer.dto.DataDto;
import java.util.HashMap;


@RequiredArgsConstructor
@RestController
public class FilesController {

    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @PostMapping("/statements")
    public ResponseEntity<byte[]> sendData(@RequestBody DataDto data){

        DataDto newData = DataDto.builder()
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .build();

        Message newMessage = MessageBuilder.withBody(newData.toString().getBytes()).build();

        Message result = rabbitTemplate.sendAndReceive(RabbitConfig.RPC_EXCHANGE, RabbitConfig.RPC_QUEUE1, newMessage);

        byte[] response = null;

        if (result != null) {
            String correlationId = newMessage.getMessageProperties().getCorrelationId();

            HashMap<String, Object> headers = (HashMap<String, Object>) result.getMessageProperties().getHeaders();

            String msgId = (String) headers.get("spring_returned_message_correlation");

            if (msgId.equals(correlationId)) {
                response = result.getBody();
            }
        }

        System.err.println(response);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "out.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> resultPdf = new ResponseEntity<>(response, headers, HttpStatus.OK);
        return resultPdf;
    }
}
