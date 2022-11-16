package ro.tuc.sensorreader.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.sensorreader.MeasurementProducer;
import ro.tuc.sensorreader.config.ConfigProperties;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Service
public class SensorReader {

    private final Scanner fileReader;

    @Autowired
    public SensorReader(ConfigProperties configProperties) throws FileNotFoundException {
        File file = new File(configProperties.getSensorDataFilename());
        fileReader = new Scanner(file);
    }

    public Double getSensorMeasurement() {
        return fileReader.nextDouble();
    }

    @PreDestroy
    public void close() {
        fileReader.close();
    }
}
