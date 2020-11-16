package com.frgp.remember.ui.Vinculaciones.NuevaVinculacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VinculacionesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public VinculacionesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
