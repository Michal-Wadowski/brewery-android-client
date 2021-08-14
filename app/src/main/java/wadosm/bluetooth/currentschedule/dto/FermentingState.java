package wadosm.bluetooth.currentschedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FermentingState {

    private boolean enabled;

    private Float currentTemperature;

    private Float destinationTemperature;

    private boolean heating;

}