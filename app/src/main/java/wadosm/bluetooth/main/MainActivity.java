package wadosm.bluetooth.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import wadosm.bluetooth.R;
import wadosm.bluetooth.dependency.ViewModelProviderFactory;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements MainViewModelGetter {

    private MainViewModel model;

    @Inject
    protected ViewModelProviderFactory viewModelProviderFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = buildViewModel();

        model.getSwitchFramgmentMLD().observe(this, this::setFragment);

        model.getUpdateTitleMLD().observe(this, this::setTitle);

        model.onActivityStart();
    }

    public MainViewModel buildViewModel() {
        return viewModelProviderFactory.getViewModelProvider(this).get(MainViewModel.class);
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

    private void setFragment(NewFragmentVDO newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragmentContainerView, newFragment.getFragment());

        if (newFragment.isAddToBackStack()) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }
}