package ro.tuc.sensorreader.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.txt")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ConfigProperties {
    @Value("${EXCHANGE_NAME}")
    private String exchangeName;

    @Value("${HOST_NAME}")
    private String hostName;

    @Value("${SENSOR_DATA_FILENAME}")
    private String sensorDataFilename;

    @Value("${DEVICE_ID}")
    private String deviceId;
}
