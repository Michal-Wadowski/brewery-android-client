package wadosm.brewingclient.currentschedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class BrewingState {

    private boolean enabled;

    private Float currentTemperature;

    private Float destinationTemperature;

    private Integer maxPower;

    private Float powerTemperatureCorrelation;

    private Integer timeElapsed;

    private boolean motorEnabled;

    private boolean temperatureAlarm;

    private Integer heatingPower;

}