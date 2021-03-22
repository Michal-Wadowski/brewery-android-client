package wadosm.bluetooth.currentstate.model;

import org.junit.Test;

import java.util.Arrays;

import wadosm.bluetooth.connectivity.model.StateElement;
import wadosm.bluetooth.connectivity.model.StateElements;

import static org.assertj.core.api.Assertions.assertThat;
import static wadosm.bluetooth.currentstate.model.StateElementsVDO.ELEMENT_MARGIN_SIZE;

public class StateElementsVDOTest {

    @Test
    public void should_convert_state_elements_to_VDO() {
        // given
        StateElements stateItems = new StateElements(
                Arrays.asList(
                        new StateElement("Temp 1", StateElement.Type.TEMPERATURE_SENSOR, "75.1 °C")
                )
        );

        // when
        StateElementsVDO elementsVDO = new StateElementsVDO(0, stateItems);

        // then
        assertThat(elementsVDO.getItems().size()).isEqualTo(1);
    }

    @Test
    public void should_not_split_VDO_elements_into_rows_if_fit_to_max_width() {
        // given
        StateElement e1 = new StateElement("Temp 1", StateElement.Type.TEMPERATURE_SENSOR, "75.1 °C");
        StateElement e2 = new StateElement("Heater 1", StateElement.Type.HEATING_ELEMENT, "50%");

        int maxLength = (new StateElementVDO(e1)).getLength() + ELEMENT_MARGIN_SIZE + (new StateElementVDO(e2)).getLength() + ELEMENT_MARGIN_SIZE;

        StateElements stateItems = new StateElements(
                Arrays.asList(e1, e2)
        );

        // when
        StateElementsVDO elementsVDO = new StateElementsVDO(maxLength, stateItems);

        // then
        assertThat(elementsVDO.getItems().size()).isEqualTo(1);
    }

    @Test
    public void should_split_VDO_elements_into_rows_if_not_fit_to_max_width() {
        // given
        StateElement e1 = new StateElement("Temp 1", StateElement.Type.TEMPERATURE_SENSOR, "75.1 °C");
        StateElement e2 = new StateElement("Heater 1", StateElement.Type.HEATING_ELEMENT, "50%");

        int maxLength = (new StateElementVDO(e1)).getLength() + ELEMENT_MARGIN_SIZE + (new StateElementVDO(e2)).getLength() + ELEMENT_MARGIN_SIZE - 1;

        StateElements stateItems = new StateElements(
                Arrays.asList(e1, e2)
        );

        // when
        StateElementsVDO elementsVDO = new StateElementsVDO(maxLength, stateItems);

        // then
        assertThat(elementsVDO.getItems().size()).isEqualTo(2);
    }

    @Test
    public void should_split_VDO_elements_into_rows_if_not_fit_to_max_width2() {
        // given
        StateElement e1 = new StateElement("Temp 1", StateElement.Type.TEMPERATURE_SENSOR, "75.1 °C");
        StateElement e2 = new StateElement("Heater 1", StateElement.Type.HEATING_ELEMENT, "50%");
        StateElement e3 = new StateElement("Heater 2", StateElement.Type.HEATING_ELEMENT, "50%");

        int maxLength = (new StateElementVDO(e1)).getLength() + ELEMENT_MARGIN_SIZE + (new StateElementVDO(e2)).getLength() + ELEMENT_MARGIN_SIZE - 1;

        StateElements stateItems = new StateElements(
                Arrays.asList(e1, e2, e3)
        );

        // when
        StateElementsVDO elementsVDO = new StateElementsVDO(maxLength, stateItems);

        // then
        assertThat(elementsVDO.getItems().size()).isEqualTo(2);
    }

}