package wadosm.bluetooth.currentstate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import wadosm.bluetooth.R;
import wadosm.bluetooth.currentstate.stateitem.StateItemsVDO;
import wadosm.bluetooth.currentstate.stateitem.StateVDO;
import wadosm.bluetooth.dependency.ViewModelProviderFactory;

@AndroidEntryPoint
public class CurrentStateFragment extends Fragment {

    public static CurrentStateFragment newInstance() {
        return new CurrentStateFragment();
    }

    @Inject
    protected ViewModelProviderFactory viewModelProviderFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.fragment_current_state, container, false);

        CurrentStateViewModel model = buildModel();

        model.onFragmentInit(requireActivity());

        return currentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TableLayout layout = view.findViewById(R.id.stateItemsContainer);

        StateItemsVDO stateItems = new StateItemsVDO();


        stateItems.addRow(Arrays.asList(new StateVDO("Foo"), new StateVDO("Bar")));
        stateItems.addRow(Arrays.asList(new StateVDO("Biz"), new StateVDO("Buz"), new StateVDO("XXX")));

        List<List<StateVDO>> items = stateItems.getItems();
        for (List<StateVDO> rows : items) {
            TableRow row = new TableRow(getContext());

            for (StateVDO item : rows) {
                Button textView = new Button(getContext());
                textView.setText(item.getName());

                TableRow.LayoutParams param = new TableRow.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1.0f
                );
                textView.setLayoutParams(param);

                row.addView(textView);
            }

            layout.addView(row);
        }
    }

    private CurrentStateViewModel buildModel() {
        return viewModelProviderFactory.getViewModelProvider(this).get(CurrentStateViewModel.class);
    }

}
