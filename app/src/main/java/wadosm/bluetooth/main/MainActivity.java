package wadosm.bluetooth.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import wadosm.bluetooth.R;

public class MainActivity extends AppCompatActivity implements MainActivityModelGetter {

    public static ModelFactory modelFactory = new DefaultModelFactory();

    public static FragmentFactory fragmentFactory = new FragmentFactory();

    MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = modelFactory.getModel(this);

        model.getSwitchFramgmentMLD().observe(this, this::setFragment);

        model.getUpdateTitleMLD().observe(this, this::setTitle);

        if (savedInstanceState == null) {
            Fragment fragment = fragmentFactory.getMachineryConnectFragment();

            model.getSwitchFramgmentMLD().postValue(fragment);
        }
    }

    @Override
    public MainViewModel getModel() {
        return model;
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, fragment, null)
                .commit();
    }

    private static class DefaultModelFactory implements ModelFactory {
        @Override
        public MainViewModel getModel(MainActivity owner) {
            return new ViewModelProvider(owner).get(MainViewModelImpl.class);
        }
    }
}