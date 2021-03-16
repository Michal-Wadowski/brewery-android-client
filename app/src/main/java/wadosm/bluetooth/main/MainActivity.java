package wadosm.bluetooth.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import wadosm.bluetooth.R;


public class MainActivity extends AppCompatActivity implements MainViewModelGetter {

    private static DependencyFactory dependencyFactory = new DependencyFactory();

    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = getDependencyFactory().getModel(this);

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

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
        }
    }

    public static void setDependencyFactory(DependencyFactory dependencyFactory) {
        MainActivity.dependencyFactory = dependencyFactory;
    }

    public static DependencyFactory getDependencyFactory() {
        return dependencyFactory;
    }

    private void setFragment(NewFragmentVDO newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragmentContainerView, newFragment.getFragment());

        if (newFragment.isAddToBackStack()) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    public static class DependencyFactory {
        public MainViewModel getModel(MainActivity owner) {
            return new ViewModelProvider(owner).get(MainViewModel.class);
        }
    }
}