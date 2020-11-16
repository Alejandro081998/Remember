package com.frgp.remember.ui.Seguimiento.Detalle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetallePerfilViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DetallePerfilViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}