package com.frgp.remember.ui.Vinculaciones.Pendientes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VinculacionesPendientesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public VinculacionesPendientesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
