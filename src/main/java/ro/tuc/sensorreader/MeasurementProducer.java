package ro.tuc.sensorreader;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import ro.tuc.sensorreader.config.ConfigProperties;
import ro.tuc.sensorreader.dto.MeasurementDTO;
import ro.tuc.sensorreader.services.MeasurementSender;
import ro.tuc.sensorreader.services.SensorReader;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableAsync
public class MeasurementProducer {

    public static void main(String[] argv) {
        SpringApplication.run(MeasurementProducer.class, argv);
    }
}