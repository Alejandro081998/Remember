package com.frgp.remember.ui.Rutinas.Supervisores;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SeleccionPacienteRutinaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SeleccionPacienteRutinaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}