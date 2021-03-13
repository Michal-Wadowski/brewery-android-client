package wadosm.bluetooth;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import wadosm.bluetooth.machineryconnect.MachineryConnectFragment;
import wadosm.bluetooth.main.MainActivity;
import wadosm.bluetooth.main.MainViewModel;
import wadosm.bluetooth.main.MainViewModelImpl;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, true, false);


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_call_model_onActivityStart_after_start() throws Throwable {
        // given
        MainViewModel modelMock = mock(MainViewModel.class);
        when(modelMock.getUpdateTitleMLD()).thenReturn(new MutableLiveData<>());
        when(modelMock.getSwitchFramgmentMLD()).thenReturn(new MutableLiveData<>());

        MainActivity.setDependencyFactory(owner -> modelMock);

        // when
        activityRule.launchActivity(null);

        // then
        verify(modelMock, times(1)).onActivityStart();
    }

    @Test
    public void should_switch_to_selected_fragment_on_switchFramgmentMLD() throws Throwable {
        // given

        activityRule.runOnUiThread(() -> {
            Fragment expectedFragment = MachineryConnectFragment.newInstance();

            MainViewModel model = new MainViewModelImpl() {
                @Override
                public void onActivityStart() {
                    getSwitchFramgmentMLD().postValue(expectedFragment);
                }
            };
            MainActivity.setDependencyFactory(owner -> model);
        });

        // when
        activityRule.launchActivity(null);

        // then
        onView(withId(R.id.machineryConnectFragment)).check(matches(isDisplayed()));

    }

}