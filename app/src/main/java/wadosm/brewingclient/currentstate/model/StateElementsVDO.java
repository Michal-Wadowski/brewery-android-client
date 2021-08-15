package wadosm.brewingclient.currentstate.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wadosm.brewingclient.connectivity.model.StateElement;
import wadosm.brewingclient.connectivity.model.StateElements;

@Getter
@NoArgsConstructor
public class StateElementsVDO {

    public static final int ELEMENT_MARGIN_SIZE = 3;

    private List<List<StateElementVDO>> items;

    public StateElementsVDO(int maxWidth, StateElements stateItems) {
        items = new ArrayList<>();

        int rowLength = 0;
        List<StateElementVDO> row = new ArrayList<>();

        for (StateElement item : stateItems.getItems()) {
            StateElementVDO itemVDO = new StateElementVDO(item);

            if (rowLength + itemVDO.getLength() + ELEMENT_MARGIN_SIZE > maxWidth) {
                if (!row.isEmpty()) {
                    items.add(row);
                }
                row = new ArrayList<>();
                rowLength = 0;
            }

            row.add(itemVDO);

            rowLength += itemVDO.getLength() + 3;
        }

        if (!row.isEmpty()) {
            items.add(row);
        }
    }

}

