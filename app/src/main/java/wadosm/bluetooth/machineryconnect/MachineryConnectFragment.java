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
import wadosm.bluetooth.main.MainActivity;
import wadosm.bluetooth.main.PublicMainViewModel;

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

        connectButton.setOnClickListener(buttonView -> model.onConnectButton());

        model.onFragmentInit(getMainActivityModel());

        return currentView;
    }

    private void updateMessageBox(Integer text) {
        messagesBox.setText(text);
    }

    public PublicMainViewModel getMainActivityModel() {
        if (getActivity() instanceof MainActivity) {
            return ((MainActivity) getActivity()).getModel();
        } else
            return null;
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
