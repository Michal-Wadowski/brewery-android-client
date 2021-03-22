package wadosm.bluetooth.currentstate.model;

import lombok.Getter;
import wadosm.bluetooth.connectivity.model.StateElement;

@Getter
public class StateElementVDO {

    private String name;

    private String value;

    public StateElementVDO(StateElement stateElement) {
        name = stateElement.getName();
        value = stateElement.getValue();
    }

    public int getLength() {
        return name.length() + value.length();
    }
}
