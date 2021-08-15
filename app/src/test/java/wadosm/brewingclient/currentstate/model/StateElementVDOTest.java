package wadosm.brewingclient.currentstate.model;

import org.junit.Test;

import wadosm.brewingclient.connectivity.model.StateElement;

import static org.assertj.core.api.Assertions.assertThat;

public class StateElementVDOTest {

    @Test
    public void should_convert_state_element_to_VDO() {
        // given
        StateElement element = new StateElement("Temp 1", StateElement.Type.TEMPERATURE_SENSOR, "75.1 °C");

        // when
        StateElementVDO elementVDO = new StateElementVDO(element);

        // then
        assertThat(elementVDO.getName()).isEqualTo("Temp 1");
        assertThat(elementVDO.getValue()).isEqualTo("75.1 °C");
        assertThat(elementVDO.getLength()).isEqualTo(("Temp 1" + "75.1 °C").length());
    }

}