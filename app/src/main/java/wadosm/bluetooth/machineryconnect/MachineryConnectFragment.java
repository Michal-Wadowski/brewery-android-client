package wadosm.bluetooth.machineryconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import wadosm.bluetooth.R;
import wadosm.bluetooth.dependency.ViewModelProviderFactory;
import wadosm.bluetooth.machineryconnect.model.MachineryConnectVDO;

@AndroidEntryPoint
public class MachineryConnectFragment extends Fragment {

    @Inject
    protected ViewModelProviderFactory viewModelProviderFactory;

    private TextView messagesBox;
    private Button connectButton;

    public static MachineryConnectFragment newInstance() {
        return new MachineryConnectFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = buildViews(inflater, container);

        MachineryConnectViewModel model = buildModel();

        setListeners(model);

        model.onFragmentInit(getActivity());

        return currentView;
    }

    private View buildViews(LayoutInflater inflater, ViewGroup container) {
        View currentView = inflater.inflate(R.layout.fragment_machinery_connect, container, false);

        messagesBox = currentView.findViewById(R.id.machineryConnect_messagesBox);
        connectButton = currentView.findViewById(R.id.machineryConnect_connectButton);
        return currentView;
    }

    private void setListeners(MachineryConnectViewModel model) {
        model.getMachineryConnectMLD().observe(getViewLifecycleOwner(), this::updateMachineryConnectView);

        connectButton.setOnClickListener(buttonView -> model.onConnectButton(getActivity()));
    }

    private void updateMachineryConnectView(MachineryConnectVDO content) {
        if (content.getMessageBox().getStringId() != null) {
            messagesBox.setText(content.getMessageBox().getStringId());
        } else if (content.getMessageBox().getCustomText() != null) {
            messagesBox.setText(content.getMessageBox().getCustomText());
        }
        connectButton.setEnabled(content.isConnectButtonEnable());
    }

    private MachineryConnectViewModel buildModel() {
        return viewModelProviderFactory.getViewModelProvider(this).get(MachineryConnectViewModel.class);
    }
}
