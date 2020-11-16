package com.frgp.remember.ui.Rutinas.Raiz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RutinasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RutinasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}