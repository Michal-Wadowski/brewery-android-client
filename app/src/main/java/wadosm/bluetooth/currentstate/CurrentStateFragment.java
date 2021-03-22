package wadosm.bluetooth.currentstate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import wadosm.bluetooth.R;
import wadosm.bluetooth.currentstate.model.StateElementVDO;
import wadosm.bluetooth.currentstate.model.StateElementsVDO;
import wadosm.bluetooth.currentstate.view.SensorViewElement;
import wadosm.bluetooth.dependency.ViewModelProviderFactory;

import static android.widget.LinearLayout.HORIZONTAL;

@AndroidEntryPoint
public class CurrentStateFragment extends Fragment {

    public static CurrentStateFragment newInstance() {
        return new CurrentStateFragment();
    }

    private CurrentStateViewModel model;

    @Inject
    protected ViewModelProviderFactory viewModelProviderFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.fragment_current_state, container, false);

        model = buildModel();

        model.onFragmentInit(requireActivity());

        model.getStateItemsMLD().observe(getViewLifecycleOwner(), this::updateStateItemsView);

        return currentView;
    }

    @Override
    public void onDetach() {
        model.onFragmentDetach(getActivity());

        super.onDetach();
    }

    private void updateStateItemsView(StateElementsVDO stateItemsVDO) {
        LinearLayout layout = getView().findViewById(R.id.stateItemsContainer);
        layout.removeAllViews();

        List<List<StateElementVDO>> items = stateItemsVDO.getItems();
        for (List<StateElementVDO> rows : items) {
            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(HORIZONTAL);
            for (StateElementVDO item : rows) {
                SensorViewElement textView = new SensorViewElement(getContext(), item);

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
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
