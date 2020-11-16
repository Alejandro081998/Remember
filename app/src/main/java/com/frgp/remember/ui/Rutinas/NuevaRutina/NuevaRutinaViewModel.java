package com.frgp.remember.ui.Rutinas.NuevaRutina;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NuevaRutinaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NuevaRutinaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
