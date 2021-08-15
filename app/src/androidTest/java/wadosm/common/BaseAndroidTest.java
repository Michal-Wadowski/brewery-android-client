package wadosm.common;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dagger.hilt.android.testing.HiltAndroidRule;
import wadosm.brewingclient.dependency.MainDependencyProvider;
import wadosm.brewingclient.dependency.ViewModelProviderFactory;
import wadosm.brewingclient.main.MainActivity;
import wadosm.brewingclient.main.MainViewModel;
import wadosm.brewingclient.main.model.NewFragmentVDO;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseAndroidTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Rule
    public InstantTaskExecutorRule instantRule = new InstantTaskExecutorRule();

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    protected final MutableLiveData<Integer> updateTitleMLD = new MutableLiveData<>();

    protected final MutableLiveData<NewFragmentVDO> switchFramgmentMLD = new MutableLiveData<>();

    protected MainViewModel mainViewModel;

    @Mock
    protected ViewModelProviderFactory viewModelProviderFactory;

    @Mock
    protected ViewModelProvider viewModelProvider;

    @Before
    public void setUpBaseAndroid() {
        MockitoAnnotations.openMocks(this);
        hiltRule.inject();

        MainDependencyProvider.viewModelProviderFactory = viewModelProviderFactory;
        when(viewModelProviderFactory.getViewModelProvider(any())).thenReturn(viewModelProvider);
    }

    protected void prepareMainViewModel() {
        mainViewModel = getMainViewModel();
        when(viewModelProvider.get(eq(MainViewModel.class))).thenReturn(mainViewModel);
    }

    protected void launchMainActivity() {
        activityRule.launchActivity(null);
    }

    protected void launchMainActivity(Fragment fragment) {
        launchMainActivity();

        mainViewModel.getSwitchFramgmentMLD().postValue(new NewFragmentVDO(fragment, false));
    }

    private MainViewModel getMainViewModel() {
        MainViewModel modelMock = mock(MainViewModel.class);
        when(modelMock.getUpdateTitleMLD()).thenReturn(updateTitleMLD);
        when(modelMock.getSwitchFramgmentMLD()).thenReturn(switchFramgmentMLD);
        return modelMock;
    }
}