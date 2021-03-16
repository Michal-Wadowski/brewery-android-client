package wadosm.bluetooth.currentstate.stateitem;

import java.util.ArrayList;
import java.util.List;

public class StateItemsVDO {

    private List<List<StateVDO>> items = new ArrayList<>();

    public StateItemsVDO() {
    }

    public void addRow(List<StateVDO> row) {
        items.add(row);
    }

    public List<List<StateVDO>> getItems() {
        return items;
    }
}
