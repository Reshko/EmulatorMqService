package ru.example.emulator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.example.emulator.utils.HexToBin;

import javax.jms.JMSException;
import javax.jms.Message;

import java.io.IOException;


@Slf4j
@Component
public class Listener {

    @Autowired
    private MQStub mqStub;

    @JmsListener(destination = "INFO.FACADE.MTS.EIP.POOLED.IN", concurrency = "5-20")
    public void receiveMessage(Message msg) throws JMSException, IOException {

        log.info("recive message: '{}'", msg.getJMSMessageID());

        byte[] messageId = HexToBin.hexToBin(msg.getJMSMessageID(), 3);
        byte[] correlationId = HexToBin.hexToBin("ID:666000000000000000000000000000000000000000000000", 3);

        mqStub.sendMqMessage(messageId, correlationId, msg.getBody(String.class), msg.getJMSMessageID());

    }
}