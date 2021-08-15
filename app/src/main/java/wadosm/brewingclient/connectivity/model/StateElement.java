package wadosm.brewingclient.connectivity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StateElement {

    private String name;

    private Type type;

    private String value;

    public enum Type {
        TEMPERATURE_SENSOR, HEATING_ELEMENT, ALARM, OUTPUT
    }
}
