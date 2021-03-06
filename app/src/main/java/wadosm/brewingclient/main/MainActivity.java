package wadosm.brewingclient.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import wadosm.brewingclient.R;
import wadosm.brewingclient.dependency.ViewModelProviderFactory;
import wadosm.brewingclient.main.model.NewFragmentVDO;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements MainViewModelGetter {

    private MainViewModel model;

    @Inject
    protected ViewModelProviderFactory viewModelProviderFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = buildViewModel();

        buildViews();

        setListeners();

        model.onActivityInit();
    }



    private void buildViews() {
        setContentView(R.layout.activity_main);
    }

    private void setListeners() {
        model.getSwitchFramgmentMLD().observe(this, this::setFragment);

        model.getUpdateTitleMLD().observe(this, this::setTitle);
    }

    private MainViewModel buildViewModel() {
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