package com.frgp.remember.ui.Perfil.PerfilDetalle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PerfilDetalleViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PerfilDetalleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
