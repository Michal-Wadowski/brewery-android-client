package wadosm.bluetooth.machineryconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import wadosm.bluetooth.R;

public class MachineryConnectFragment extends Fragment {

    private static DependencyFactory dependencyFactory = new DependencyFactory();

    private TextView messagesBox;
    private Button connectButton;

    public static MachineryConnectFragment newInstance() {
        return new MachineryConnectFragment();
    }

    public static DependencyFactory getDependencyFactory() {
        return dependencyFactory;
    }

    public static void setDependencyFactory(DependencyFactory dependencyFactory) {
        MachineryConnectFragment.dependencyFactory = dependencyFactory;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.fragment_machinery_connect, container, false);

        messagesBox = currentView.findViewById(R.id.machineryConnect_messagesBox);
        connectButton = currentView.findViewById(R.id.machineryConnect_connectButton);

        MachineryConnectViewModel model = getDependencyFactory().getModel(this);

        model.getMachineryConnectMLD().observe(getViewLifecycleOwner(), this::updateMachineryConnectView);

        connectButton.setOnClickListener(buttonView -> model.onConnectButton(getContext()));

        model.onFragmentInit(getContext());

        return currentView;
    }

    private void updateMachineryConnectView(MachineryConnectVDO content) {
        if (content.getMessageBox().getStringId() != null) {
            messagesBox.setText(content.getMessageBox().getStringId());
        } else if (content.getMessageBox().getCustomText() != null) {
            messagesBox.setText(content.getMessageBox().getCustomText());
        }
        connectButton.setEnabled(content.isConnectButtonEnable());
    }

    public static class DependencyFactory {
        public MachineryConnectViewModel getModel(MachineryConnectFragment owner) {
            return new ViewModelProvider(owner).get(MachineryConnectViewModel.class);
        }
    }
}
