package br.com.mbcustom.purodesejostore.ui.lubrificantes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LubrificantesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LubrificantesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue( "This is gallery fragment" );
    }

    public LiveData<String> getText() {
        return mText;
    }
}