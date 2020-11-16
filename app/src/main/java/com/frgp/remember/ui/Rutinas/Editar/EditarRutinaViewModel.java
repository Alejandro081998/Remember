package com.frgp.remember.ui.Rutinas.Editar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditarRutinaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EditarRutinaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
