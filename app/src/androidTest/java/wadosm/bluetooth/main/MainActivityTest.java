package wadosm.bluetooth.main;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
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

import wadosm.bluetooth.R;
import wadosm.bluetooth.machineryconnect.MachineryConnectFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Mock
    private MutableLiveData<Integer> updateTitleMLD;

    @Mock
    private MutableLiveData<NewFragmentVDO> switchFramgmentMLD;

    @Mock
    public MainActivity.DependencyFactory dependencyFactory;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_call_model_onActivityStart_after_start() throws Throwable {
        // given
        MainViewModel modelMock = getModelMock();

        setDependencyFactory(modelMock);

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

            MainViewModel model = new MainViewModel() {
                @Override
                public void onActivityStart() {
                    getSwitchFramgmentMLD().postValue(new NewFragmentVDO(expectedFragment, false));
                }
            };

            setDependencyFactory(model);
        });

        // when
        activityRule.launchActivity(null);

        // then
        onView(ViewMatchers.withId(R.id.machineryConnectFragment)).check(matches(isDisplayed()));
    }

    @Test
    public void should_block_back_button_if_last_fragment() throws Throwable {
        // given
        MainViewModel modelMock = getModelMock();

        setDependencyFactory(modelMock);

        activityRule.launchActivity(null);

        activityRule.runOnUiThread(() -> {
            modelMock.getSwitchFramgmentMLD().postValue(new NewFragmentVDO(new Fragment(), true));
        });

        // when
        Espresso.pressBack();

        // then
        onView(ViewMatchers.withId(R.id.fragmentContainerView)).check(matches(isDisplayed()));
    }

    private void setDependencyFactory(MainViewModel modelMock) {
        when(dependencyFactory.getModel(any())).thenReturn(modelMock);
        MainActivity.setDependencyFactory(dependencyFactory);
    }

    private MainViewModel getModelMock() {
        MainViewModel modelMock = mock(MainViewModel.class);
        when(modelMock.getUpdateTitleMLD()).thenReturn(updateTitleMLD);
        when(modelMock.getSwitchFramgmentMLD()).thenReturn(switchFramgmentMLD);
        return modelMock;
    }
}