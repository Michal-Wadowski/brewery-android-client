package wadosm.bluetooth.machineryconnect;

import androidx.lifecycle.MutableLiveData;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import wadosm.bluetooth.R;
import wadosm.bluetooth.main.FragmentFactory;
import wadosm.bluetooth.main.PublicMainViewModel;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MachineryConnectViewModelTest extends TestCase {

    @Mock
    private PublicMainViewModel publicMainViewModel;

    @Mock
    private MutableLiveData<Integer> updateTitleMLD;

    @Test
    public void should_MachineryConnectFragment_set_title_onActivityStart() {
        // given

        when(publicMainViewModel.getUpdateTitleMLD()).thenReturn(updateTitleMLD);

        MachineryConnectViewModel.setDependencyFactory(FragmentFactory::new);

        MachineryConnectViewModel model = new MachineryConnectViewModel();

        // when
        model.onFragmentInit(publicMainViewModel);

        // then
        verify(updateTitleMLD, times(1)).postValue(R.string.machineryConnect_connectToDevice);
    }

}