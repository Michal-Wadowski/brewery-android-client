package wadosm.bluetooth.main.model;

import androidx.fragment.app.Fragment;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class NewFragmentVDO {

    private final Fragment fragment;

    private final boolean addToBackStack;

}
