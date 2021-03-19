package wadosm.bluetooth.dependency;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public interface ViewModelProviderFactory {
    ViewModelProvider getViewModelProvider(ViewModelStoreOwner owner);
}
