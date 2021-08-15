package wadosm.brewingclient.currentstate;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.testing.HiltAndroidTest;
import lombok.Getter;
import wadosm.brewingclient.connectivity.model.StateElement;
import wadosm.brewingclient.currentstate.model.StateElementVDO;
import wadosm.brewingclient.currentstate.model.StateElementsVDO;
import wadosm.common.BaseAndroidTest;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@HiltAndroidTest
public class CurrentStateFragmentTest extends BaseAndroidTest {

    private final MutableLiveData<StateElementsVDO> stateElementsMLD = new MutableLiveData<>();

    @Test
    public void should_show_temperature_state() {
        // given
        prepareMainViewModel();

        CurrentStateViewModel currentStateViewModel = prepareCurrentStateViewModel();

        Fragment fragment = CurrentStateFragment.newInstance();
        launchMainActivity(fragment);

        StateElementsVDO stateElements = new TestStateElementsVDO(new StateElement("Temp 1", StateElement.Type.TEMPERATURE_SENSOR, "75.1 °C"));

        // when
        currentStateViewModel.getStateItemsMLD().postValue(stateElements);

        // then

        Espresso.onView(ViewMatchers.withText("Temp 1:")).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withText("75.1 °C")).check(matches(isDisplayed()));
    }

    private CurrentStateViewModel prepareCurrentStateViewModel() {
        CurrentStateViewModel currentStateViewModel = getCurrentStateViewModel();
        when(viewModelProvider.get(eq(CurrentStateViewModel.class))).thenReturn(currentStateViewModel);
        return currentStateViewModel;
    }

    @Getter
    private class TestStateElementsVDO extends StateElementsVDO {

        private final List<List<StateElementVDO>> items;

        public TestStateElementsVDO(StateElement element) {
            List<StateElementVDO> row = new ArrayList<>();
            row.add(new StateElementVDO(element));
            items = new ArrayList<>();
            items.add(row);
        }

    }

    private CurrentStateViewModel getCurrentStateViewModel() {
        CurrentStateViewModel modelMock = mock(CurrentStateViewModel.class);
        when(modelMock.getStateItemsMLD()).thenReturn(stateElementsMLD);
        return modelMock;
    }

}