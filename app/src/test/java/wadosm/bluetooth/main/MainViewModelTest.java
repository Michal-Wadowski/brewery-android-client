package wadosm.bluetooth.main;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import wadosm.bluetooth.dependency.FragmentFactoryImpl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {

    @Mock
    MutableLiveData<NewFragmentVDO> switchFramgmentMLD;

    @Mock
    FragmentFactoryImpl fragmentFactory;

    @Test
    public void should_start_MachineryConnectFragment_onActivityStart_if_no_other_screes() {
        // given
        Fragment expectedFragment = new Fragment();

        when(fragmentFactory.getMachineryConnectFragment()).thenReturn(expectedFragment);

        MainViewModel model = new MainViewModel(fragmentFactory) {
            @Override
            public MutableLiveData<NewFragmentVDO> getSwitchFramgmentMLD() {
                return switchFramgmentMLD;
            }
        };

        // when
        model.onActivityStart();

        // then
        verify(switchFramgmentMLD, times(1)).postValue(
                new NewFragmentVDO(expectedFragment, false)
        );
    }

    @Test
    public void should_skip_MachineryConnectFragment_onActivityStart_if_there_is_some_screen() {
        // given
        Fragment expectedFragment = new Fragment();

        when(fragmentFactory.getMachineryConnectFragment()).thenReturn(expectedFragment);

        MainViewModel model = new MainViewModel(fragmentFactory) {
            @Override
            public MutableLiveData<NewFragmentVDO> getSwitchFramgmentMLD() {
                return switchFramgmentMLD;
            }
        };

        // when
        model.onActivityStart();
        model.onActivityStart();

        // then
        verify(switchFramgmentMLD, times(1)).postValue(
                new NewFragmentVDO(expectedFragment, false)
        );
    }

}