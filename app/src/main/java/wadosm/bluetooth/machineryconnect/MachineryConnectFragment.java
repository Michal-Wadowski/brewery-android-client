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

    static DependencyFactory dependencyFactory = new DefaulDependencyFactory();

    MachineryConnectViewModel model;

    TextView messagesBox;
    Button connectButton;

    public static MachineryConnectFragment newInstance() {
        return new MachineryConnectFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.fragment_machinery_connect, container, false);

        messagesBox = currentView.findViewById(R.id.machineryConnect_messagesBox);
        connectButton = currentView.findViewById(R.id.machineryConnect_connectButton);

        model = dependencyFactory.getModel(this);

        model.getMessagesBoxMLD().observe(getViewLifecycleOwner(), this::updateMessageBox);

        model.getConnectButtonEnableMLD().observe(getViewLifecycleOwner(), this::connectButtonEnable);

        connectButton.setOnClickListener(buttonView -> model.onConnectButton(getContext()));

        model.onFragmentInit(getContext());

        return currentView;
    }

    private void updateMessageBox(MessageBoxContent content) {
        if (content.getStringId() != null) {
            messagesBox.setText(content.getStringId());
        } else if (content.getCustomText() != null) {
            messagesBox.setText(content.getCustomText());
        }
    }

    private void connectButtonEnable(Boolean enable) {
        connectButton.setEnabled(enable);
    }

    public interface DependencyFactory {
        MachineryConnectViewModel getModel(MachineryConnectFragment owner);
    }

    public static class DefaulDependencyFactory implements DependencyFactory {
        @Override
        public MachineryConnectViewModel getModel(MachineryConnectFragment owner) {
            return new ViewModelProvider(owner).get(MachineryConnectViewModel.class);
        }
    }
}
