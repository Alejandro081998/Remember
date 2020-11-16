package com.frgp.remember.ui.Vinculaciones.Listado;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListadoVinculacionesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListadoVinculacionesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
