package wadosm.bluetooth;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import wadosm.bluetooth.machineryconnect.MachineryConnectFragment;
import wadosm.bluetooth.main.FragmentFactory;
import wadosm.bluetooth.main.MainViewModelImpl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MainViewModelImplTest {

    @Mock
    MutableLiveData<Fragment> switchFramgmentMLD;

    @Mock
    FragmentFactory fragmentFactory;

    @Test
    public void should_start_MachineryConnectFragment_onActivityStart_if_no_other_screes() {
        // given
        Fragment expectedFragment = MachineryConnectFragment.newInstance();

        when(fragmentFactory.getMachineryConnectFragment()).thenReturn(expectedFragment);

        MainViewModelImpl.setDependencyFactory(() -> fragmentFactory);

        MainViewModelImpl model = new MainViewModelImpl() {
            @Override
            public MutableLiveData<Fragment> getSwitchFramgmentMLD() {
                return switchFramgmentMLD;
            }
        };

        // when
        model.onActivityStart();

        // then
        verify(switchFramgmentMLD, times(1)).postValue(expectedFragment);
    }

}