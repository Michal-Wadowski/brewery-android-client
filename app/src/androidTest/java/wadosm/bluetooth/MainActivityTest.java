package wadosm.bluetooth;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import wadosm.bluetooth.machineryconnect.MachineryConnectFragment;
import wadosm.bluetooth.main.FragmentFactory;
import wadosm.bluetooth.main.MainActivity;
import wadosm.bluetooth.main.MainViewModel;
import wadosm.bluetooth.main.MainViewModelImpl;
import wadosm.bluetooth.main.ModelFactory;

import static org.mockito.Mockito.verify;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Mock
    MutableLiveData<Fragment> switchFramgmentMLD;

    @Mock
    MutableLiveData<String> updateTitleMLD;

    ModelFactory modelFactory = new TestModelFactory();
    FragmentFactory fragmentFactory = new TestFragmentFactory();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_after_start_switch_to_machinery_connect_fragment() throws Throwable {
        // given
        MainActivity.modelFactory = modelFactory;
        MainActivity.fragmentFactory = fragmentFactory;

        // when
        activityRule.launchActivity(null);

        // then
        activityRule.runOnUiThread(() -> verify(switchFramgmentMLD).postValue(fragmentFactory.getMachineryConnectFragment()));

    }

    static class TestFragmentFactory extends FragmentFactory {

        Fragment fragment;

        @Override
        public Fragment getMachineryConnectFragment() {
            if (fragment == null) {
                fragment = MachineryConnectFragment.newInstance();
            }
            return fragment;
        }
    }

    class TestModelFactory implements ModelFactory {

        @Override
        public MainViewModel getModel(MainActivity owner) {
            return new MainViewModelImpl() {
                @Override
                public MutableLiveData<Fragment> getSwitchFramgmentMLD() {
                    return switchFramgmentMLD;
                }

                @Override
                public MutableLiveData<String> getUpdateTitleMLD() {
                    return updateTitleMLD;
                }
            };
        }
    }
}