package wadosm.bluetooth.dependency;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public class ViewModelProviderFactoryImpl implements ViewModelProviderFactory {

    @Override
    public ViewModelProvider getViewModelProvider(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner);
    }

}
