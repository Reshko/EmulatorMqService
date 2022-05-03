package ru.example.emulator;

import java.io.IOException;
import java.util.Hashtable;

import com.ibm.mq.*;
import com.ibm.mq.constants.MQConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.example.emulator.services.*;

import javax.annotation.PostConstruct;


@Slf4j
@Component
public class MQStub {

    @Value("${ibm.mq.host}")
    private String host;

    @Value("${ibm.mq.queueManager}")
    private String queueManager;

    @Value("${ibm.mq.channel}")
    private String channel;

    @Value("${ibm.mq.user}")
    private String user;

    @Value("${ibm.mq.password}")
    private String password;

    @Value("${ibm.mq.bankResponse}")
    private String bankResponse;

    static MQQueue bankResponseQueue;

    // нативное подключения к очереди IBM MQ
    @PostConstruct
    public void connectMQ() {

        int openOptions = MQConstants.MQOO_INQUIRE + MQConstants.MQOO_OUTPUT
                + MQConstants.MQOO_FAIL_IF_QUIESCING + MQConstants.MQOO_INPUT_SHARED;

        Hashtable<String, String> props = new Hashtable<>();

        props.put("CHLTYPE", "SVRCONN");

        props.put(MQConstants.CHANNEL_PROPERTY, channel);

        props.put(MQConstants.HOST_NAME_PROPERTY, host);

        props.put(MQConstants.USER_ID_PROPERTY, user);

        props.put(MQConstants.PASSWORD_PROPERTY, password);

        props.put("SSL_REQUIRED", "false");

        try {
            MQQueueManager MQManager = new MQQueueManager(queueManager, props);

            log.info("connected to queue manager: '{}'", queueManager);

            bankResponseQueue = MQManager.accessQueue(bankResponse, openOptions);

            log.info("subscribed to topic '{}'", bankResponseQueue.getName());

        } catch (MQException e) {
            log.error("MQException CC= '{}' RC= '{}'", e.completionCode, e.reasonCode);
        }
    }

    //отправка сообщения
    @Async(value = "threadPoolTaskExecutor")
    public void sendMqMessage(byte[] msgId, byte[] corrId, String msgText, String jmsMsgId) throws IOException {

        try {

            final String MsgType = StringUtils.substringBetween(msgText, "<Name>", "</Name>");
            log.info("MsgType: '{}'", MsgType);


            MQMessage sendMsg = new MQMessage();

            sendMsg.format = MQConstants.MQFMT_STRING;

            sendMsg.characterSet = 1208;

            sendMsg.correlationId = corrId;

            sendMsg.messageId = msgId;

            String responseText = null;

            switch (MsgType) {

                case "NewsRq":
                    responseText = NewsRq.NewsRq(msgText);
                    break;
                case "PayRq":
                    responseText = PayRq.PayRq(msgText);
                    break;
                case "MapRq":
                    responseText = MapRq.MapRq(msgText);
                    break;
                case "VideoRq":
                    responseText = VideoRq.VideoRq(msgText);
                    break;
                case "MusicRq":
                    responseText = MusicRq.MusicRq(msgText);
                    break;
                default:
                    log.error("Not Found: '{}'", MsgType);
                    break;
            }

            if (responseText != null){
                sendMsg.writeString(responseText);

                bankResponseQueue.put(sendMsg);

                log.info("send message: '{}'", jmsMsgId);
            }
        } catch (MQException e) {
            log.error("MQException CC= '{}' RC= '{}'", e.completionCode, e.reasonCode);
        }
    }
}