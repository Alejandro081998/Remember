package com.frgp.remember.ui.Notificaciones.Actividades.Parentesco;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ParentescoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ParentescoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}