package br.com.mbcustom.purodesejostore.ui.acessorios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AcessoriosViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;

    public AcessoriosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue( "This is gallery fragment" );
    }

    public LiveData<String> getText() {
        return mText;
    }
}
