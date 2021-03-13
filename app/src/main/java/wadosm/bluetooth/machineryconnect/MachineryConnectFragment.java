package wadosm.bluetooth.machineryconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import wadosm.bluetooth.R;
import wadosm.bluetooth.main.MainActivity;
import wadosm.bluetooth.main.MainViewModel;

public class MachineryConnectFragment extends Fragment {

    public static MachineryConnectFragment newInstance() {
        return new MachineryConnectFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_machinery_connect, container, false);
    }

    public MainViewModel getMainActivityModel() {
        return ((MainActivity) getActivity()).getModel();
    }

    @Override
    public void onResume() {
        super.onResume();

        getMainActivityModel().getUpdateTitleMLD().postValue("Hello World!!!");
    }
}
