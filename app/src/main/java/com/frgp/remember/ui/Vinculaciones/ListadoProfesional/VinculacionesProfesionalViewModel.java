package com.frgp.remember.ui.Vinculaciones.ListadoProfesional;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VinculacionesProfesionalViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public VinculacionesProfesionalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}