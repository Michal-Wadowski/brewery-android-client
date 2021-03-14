package wadosm.bluetooth.machineryconnect;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.function.Consumer;

import wadosm.bluetooth.FragmentFactory;
import wadosm.bluetooth.R;
import wadosm.bluetooth.connectivity.DeviceConnectivity;
import wadosm.bluetooth.connectivity.DeviceService;
import wadosm.bluetooth.main.MainActivity;
import wadosm.bluetooth.main.NewFragment;
import wadosm.bluetooth.main.PublicMainViewModel;

import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MachineryConnectViewModelTest extends TestCase {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private PublicMainViewModel publicMainViewModel;

    @Mock
    private MutableLiveData<Integer> updateTitleMLD;

    @Mock
    private MutableLiveData<MessageBoxContent> messagesBoxMLD;

    @Mock
    MutableLiveData<Boolean> connectButtonEnableMLD;

    @Mock
    private MachineryConnectViewModel.DependencyFactory dependencyFactory;

    @Mock
    private DeviceConnectivity deviceConnectivity;

    @Mock
    MutableLiveData<NewFragment> switchFramgmentMLD;

    Consumer<String> onErrorCallback;

    Runnable onConnectedCallback;

    @Before
    public void setUp() {
        when(publicMainViewModel.getUpdateTitleMLD()).thenReturn(updateTitleMLD);
        MachineryConnectViewModel.setDependencyFactory(dependencyFactory);

        when(dependencyFactory.getDeviceConnectivity()).thenReturn(getDeviceConnectivityStub());
    }

    @Test
    public void should_set_title_onActivityStart() {
        // given
        MachineryConnectViewModel model = getModelWithMLDMocked();

        // when
        model.onFragmentInit(getMainActivityStub());

        // then
        verify(updateTitleMLD, times(1)).postValue(R.string.machineryConnect_connectToDevice);
    }

    @Test
    public void should_set_notConnected_onActivityStart() {
        // given
        MachineryConnectViewModel model = getModelWithMLDMocked();

        // when
        model.onFragmentInit(getMainActivityStub());

        // then
        verify(model.getMessagesBoxMLD(), times(1)).postValue(new MessageBoxContent(R.string.machineryConnect_deviceNotConnectedYet));
    }

    @Test
    public void should_enable_button_onActivityStart() {
        // given
        MachineryConnectViewModel model = getModelWithMLDMocked();

        // when
        model.onFragmentInit(getMainActivityStub());

        // then
        verify(model.getConnectButtonEnableMLD(), times(1)).postValue(true);
    }

    @Test
    public void should_connect_to_device_onConnectButton() {
        // given
        MachineryConnectViewModel model = getModelWithMLDMocked();

        when(dependencyFactory.getDeviceConnectivity()).thenReturn(deviceConnectivity);

        // when
        model.onConnectButton(null);

        // then
        verify(deviceConnectivity).connect(notNull(), notNull());
    }

    @Test
    public void should_set_connecting_text_onConnectButton() {
        // given
        MachineryConnectViewModel model = getModelWithMLDMocked();

        // when
        model.onConnectButton(null);

        // then
        verify(model.getMessagesBoxMLD(), times(1)).postValue(new MessageBoxContent(R.string.machineryConnect_connecting));
    }

    @Test
    public void should_disable_button_onConnectButton() {
        // given
        MachineryConnectViewModel model = getModelWithMLDMocked();

        // when
        model.onConnectButton(null);

        // then
        verify(model.getConnectButtonEnableMLD(), times(1)).postValue(false);
    }

    @Test
    public void should_enable_button_on_fail() {
        // given
        MachineryConnectViewModel model = getModelWithMLDMocked();

        onErrorCallback = null;

        model.onConnectButton(null);

        // when
        onErrorCallback.accept("Error message");

        // then
        verify(model.getConnectButtonEnableMLD(), times(1)).postValue(true);
    }

    @Test
    public void should_show_error_text_on_fail() {
        // given
        MachineryConnectViewModel model = getModelWithMLDMocked();

        onErrorCallback = null;

        model.onConnectButton(null);

        // when
        onErrorCallback.accept("Error message");

        // then
        verify(model.getMessagesBoxMLD(), times(1)).postValue(new MessageBoxContent("Error message"));
    }

    @Test
    public void should_go_setup_screen_on_device_connected() {
        // given
        Fragment expectedFragment = new Fragment();

        when(dependencyFactory.getFragmentFactory()).thenReturn(new FragmentFactory() {
            @Override
            public Fragment getCurrentScheduleFragment() {
                return expectedFragment;
            }
        });

        MachineryConnectViewModel model = getModelWithMLDMocked();

        when(publicMainViewModel.getSwitchFramgmentMLD()).thenReturn(switchFramgmentMLD);

        onConnectedCallback = null;

        model.onConnectButton(getMainActivityStub());

        // when
        onConnectedCallback.run();

        // then
        verify(switchFramgmentMLD).postValue(new NewFragment(expectedFragment, false));
    }

    private Context getMainActivityStub() {
        MainActivity mainActivityMock = mock(MainActivity.class);
        when(mainActivityMock.getModel()).thenReturn(publicMainViewModel);
        return mainActivityMock;
    }

    private DeviceConnectivity getDeviceConnectivityStub() {
        return new DeviceConnectivity() {
            @Override
            public boolean isConnected() {
                return false;
            }

            @Override
            public DeviceService getDeviceService() {
                return null;
            }

            @Override
            public void connect(Runnable onConnected, Consumer<String> onError) {
                onConnectedCallback = onConnected;
                onErrorCallback = onError;
            }
        };
    }

    private MachineryConnectViewModel getModelWithMLDMocked() {
        return new MachineryConnectViewModel() {
            @Override
            public MutableLiveData<Boolean> getConnectButtonEnableMLD() {
                return connectButtonEnableMLD;
            }

            @Override
            public MutableLiveData<MessageBoxContent> getMessagesBoxMLD() {
                return messagesBoxMLD;
            }
        };
    }

}