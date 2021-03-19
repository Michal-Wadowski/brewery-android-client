package wadosm.bluetooth.main;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import wadosm.bluetooth.R;
import wadosm.bluetooth.dependency.FragmentFactoryImpl;
import wadosm.bluetooth.dependency.MainDependencyProvider;
import wadosm.bluetooth.dependency.ViewModelProviderFactory;
import wadosm.bluetooth.machineryconnect.MachineryConnectFragment;
import wadosm.bluetooth.machineryconnect.MachineryConnectLogic;
import wadosm.bluetooth.machineryconnect.MachineryConnectViewModel;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@HiltAndroidTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Mock
    private MutableLiveData<Integer> updateTitleMLD;

    @Mock
    private MutableLiveData<NewFragmentVDO> switchFramgmentMLD;

    @Mock
    private ViewModelProviderFactory viewModelProviderFactory;

    @Mock
    private ViewModelProvider viewModelProvider;

    @Inject
    MachineryConnectLogic machineryConnectLogic;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        hiltRule.inject();

        MainDependencyProvider.viewModelProviderFactory = viewModelProviderFactory;
        when(viewModelProviderFactory.getViewModelProvider(any())).thenReturn(viewModelProvider);
    }

    @Test
    public void should_call_model_onActivityStart_after_start() throws Throwable {
        // given
        MainViewModel model = getModelMock();

        when(viewModelProvider.get(eq(MainViewModel.class))).thenReturn(model);

        // when
        activityRule.launchActivity(null);

        // then
        verify(model, times(1)).onActivityInit();
    }

    @Test
    public void should_switch_to_selected_fragment_on_switchFramgmentMLD() throws Throwable {
        // given

        activityRule.runOnUiThread(() -> {
            Fragment expectedFragment = MachineryConnectFragment.newInstance();

            MainViewModel mainViewModel = new MainViewModel(new FragmentFactoryImpl()) {
                @Override
                public void onActivityInit() {
                    getSwitchFramgmentMLD().postValue(new NewFragmentVDO(expectedFragment, false));
                }
            };

            when(viewModelProvider.get(eq(MainViewModel.class))).thenReturn(mainViewModel);

            when(viewModelProvider.get(eq(MachineryConnectViewModel.class))).thenReturn(new MachineryConnectViewModel(machineryConnectLogic));
        });

        // when
        activityRule.launchActivity(null);

        // then
        onView(ViewMatchers.withId(R.id.machineryConnectFragment)).check(matches(isDisplayed()));
    }

    @Test
    public void should_block_back_button_if_last_fragment() throws Throwable {
        // given
        MainViewModel model = getModelMock();

        when(viewModelProvider.get(eq(MainViewModel.class))).thenReturn(model);

        activityRule.launchActivity(null);

        activityRule.runOnUiThread(() -> {
            model.getSwitchFramgmentMLD().postValue(new NewFragmentVDO(new Fragment(), true));
        });

        // when
        Espresso.pressBack();

        // then
        onView(ViewMatchers.withId(R.id.fragmentContainerView)).check(matches(isDisplayed()));
    }


    private MainViewModel getModelMock() {
        MainViewModel modelMock = mock(MainViewModel.class);
        when(modelMock.getUpdateTitleMLD()).thenReturn(updateTitleMLD);
        when(modelMock.getSwitchFramgmentMLD()).thenReturn(switchFramgmentMLD);
        return modelMock;
    }
}