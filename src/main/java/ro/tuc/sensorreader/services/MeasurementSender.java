package ro.tuc.sensorreader.services;

import com.google.gson.*;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.sensorreader.config.ConfigProperties;
import ro.tuc.sensorreader.dto.MeasurementDTO;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.concurrent.TimeoutException;

@Service
public class MeasurementSender {

    private Channel channel;
    private Gson gson;

    private ConfigProperties configProperties;

    @Autowired
    public MeasurementSender(ConfigProperties configProperties) throws IOException, TimeoutException {
        this.configProperties = configProperties;
        setupExchange();
        setupGson();
    }

    private void setupExchange() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(configProperties.getHostName());
        factory.setPort(configProperties.getHostPort());
        factory.setUsername(configProperties.getRabbitmqUsername());
        factory.setPassword(configProperties.getRabbitmqPassword());
        factory.setVirtualHost(configProperties.getRabbitmqUsername());
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(configProperties.getExchangeName(), "fanout");

        this.channel = channel;
    }

    public void sendMeasurement(MeasurementDTO measurementDTO) throws IOException {
        String message = gson.toJson(measurementDTO);
        channel.basicPublish(configProperties.getExchangeName(), "", MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");
    }

    private void setupGson() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Timestamp.class, (JsonDeserializer<Timestamp>) (jsonElement,
                                                                                     type, context) ->
                    new Timestamp(jsonElement.getAsLong())
                )
                .registerTypeAdapter(Timestamp.class, (JsonSerializer<Timestamp>) (value, type,
                                                                                   context) ->
                    new JsonPrimitive(value.getTime())
                )
                .create();
    }
}