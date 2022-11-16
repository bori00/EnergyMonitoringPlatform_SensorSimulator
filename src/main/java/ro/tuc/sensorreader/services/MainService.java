package ro.tuc.sensorreader.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ro.tuc.sensorreader.MeasurementProducer;
import ro.tuc.sensorreader.config.ConfigProperties;
import ro.tuc.sensorreader.dto.MeasurementDTO;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@PropertySource("classpath:config.txt")
public class MainService {

    private final MeasurementSender sender;
    private final SensorReader sensorReader;
    private ConfigProperties configProperties;

    @Autowired
    public MainService(MeasurementSender measurementSender,
                       SensorReader sensorReader,
                       ConfigProperties configProperties) {
        this.sender = measurementSender;
        this.sensorReader = sensorReader;
        this.configProperties = configProperties;
    }

    @Scheduled(fixedRateString = "${INTER_READING_TIME_MILLISECONDS}", initialDelay = 1000)
    @Async
    @Autowired
    public void scheduleFixedRateTask() throws IOException {
        sender.sendMeasurement(
                new MeasurementDTO(
                        Timestamp.valueOf(LocalDateTime.now()),
                        UUID.fromString(configProperties.getDeviceId()),
                        sensorReader.getSensorMeasurement()));
    }
}
