package ro.tuc.sensorreader.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class MeasurementDTO {
    private final Timestamp timestamp;

    @SerializedName(value = "device_id")
    private final UUID deviceId;

    @SerializedName(value = "measurement_value")
    private final Double measurementValue;
}
