package wadosm.bluetooth.main;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import wadosm.bluetooth.FragmentFactory;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {

    @Mock
    MutableLiveData<NewFragment> switchFramgmentMLD;

    @Mock
    FragmentFactory fragmentFactory;

    @Test
    public void should_start_MachineryConnectFragment_onActivityStart_if_no_other_screes() {
        // given
        Fragment expectedFragment = new Fragment();

        when(fragmentFactory.getMachineryConnectFragment()).thenReturn(expectedFragment);

        MainViewModel.setDependencyFactory(() -> fragmentFactory);

        MainViewModel model = new MainViewModel() {
            @Override
            public MutableLiveData<NewFragment> getSwitchFramgmentMLD() {
                return switchFramgmentMLD;
            }
        };

        // when
        model.onActivityStart();

        // then
        verify(switchFramgmentMLD, times(1)).postValue(
                new NewFragment(expectedFragment, false)
        );
    }

    @Test
    public void should_skip_MachineryConnectFragment_onActivityStart_if_there_is_some_screen() {
        // given
        Fragment expectedFragment = new Fragment();

        when(fragmentFactory.getMachineryConnectFragment()).thenReturn(expectedFragment);

        MainViewModel.setDependencyFactory(() -> fragmentFactory);

        MainViewModel model = new MainViewModel() {
            @Override
            public MutableLiveData<NewFragment> getSwitchFramgmentMLD() {
                return switchFramgmentMLD;
            }
        };

        // when
        model.onActivityStart();
        model.onActivityStart();

        // then
        verify(switchFramgmentMLD, times(1)).postValue(
                new NewFragment(expectedFragment, false)
        );
    }

}