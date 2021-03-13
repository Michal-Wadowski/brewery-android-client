package wadosm.bluetooth.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import wadosm.bluetooth.R;


public class MainActivity extends AppCompatActivity implements MainViewModelGetter {

    static DependencyFactory dependencyFactory = new DefaulDependencyFactory();

    MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = dependencyFactory.getModel(this);

        model.getSwitchFramgmentMLD().observe(this, this::setFragment);

        model.getUpdateTitleMLD().observe(this, this::setTitle);

        if (savedInstanceState == null) {
            model.onActivityStart();
        }
    }

    @Override
    public PublicMainViewModel getModel() {
        return model;
    }

    public static void setDependencyFactory(DependencyFactory dependencyFactory) {
        MainActivity.dependencyFactory = dependencyFactory;
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, fragment, null)
                .commit();
    }

    public interface DependencyFactory {
        MainViewModel getModel(MainActivity owner);
    }

    public static class DefaulDependencyFactory implements DependencyFactory {
        @Override
        public MainViewModel getModel(MainActivity owner) {
            return new ViewModelProvider(owner).get(MainViewModelImpl.class);
        }
    }
}