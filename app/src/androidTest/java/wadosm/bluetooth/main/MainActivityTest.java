package wadosm.bluetooth.main;

import androidx.fragment.app.Fragment;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Test;

import javax.inject.Inject;

import dagger.hilt.android.testing.HiltAndroidTest;
import wadosm.bluetooth.R;
import wadosm.bluetooth.dependency.FragmentFactoryImpl;
import wadosm.bluetooth.machineryconnect.MachineryConnectFragment;
import wadosm.bluetooth.machineryconnect.MachineryConnectLogic;
import wadosm.bluetooth.machineryconnect.MachineryConnectViewModel;
import wadosm.bluetooth.main.model.NewFragmentVDO;
import wadosm.common.BaseAndroidTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@HiltAndroidTest
public class MainActivityTest extends BaseAndroidTest {

    @Inject
    MachineryConnectLogic machineryConnectLogic;


    @Test
    public void should_call_model_onActivityStart_after_start() throws Throwable {
        // given
        prepareMainViewModel();

        // when
        launchMainActivity();

        // then
        verify(mainViewModel, times(1)).onActivityInit();
    }

    @Test
    public void should_switch_to_selected_fragment_on_switchFramgmentMLD() throws Throwable {
        // given
        Fragment expectedFragment = MachineryConnectFragment.newInstance();

        MainViewModel mainViewModel = new MainViewModel(new FragmentFactoryImpl()) {
            @Override
            public void onActivityInit() {
                getSwitchFramgmentMLD().postValue(new NewFragmentVDO(expectedFragment, false));
            }
        };

        when(viewModelProvider.get(eq(MainViewModel.class))).thenReturn(mainViewModel);

        when(viewModelProvider.get(eq(MachineryConnectViewModel.class))).thenReturn(new MachineryConnectViewModel(machineryConnectLogic));

        // when
        launchMainActivity();

        // then
        onView(ViewMatchers.withId(R.id.machineryConnectFragment)).check(matches(isDisplayed()));
    }

    @Test
    public void should_block_back_button_if_last_fragment() throws Throwable {
        // given
        prepareMainViewModel();

        launchMainActivity();

        mainViewModel.switchFramgment(new NewFragmentVDO(new Fragment(), true));

        // when
        Espresso.pressBack();

        // then
        onView(ViewMatchers.withId(R.id.fragmentContainerView)).check(matches(isDisplayed()));
    }

}