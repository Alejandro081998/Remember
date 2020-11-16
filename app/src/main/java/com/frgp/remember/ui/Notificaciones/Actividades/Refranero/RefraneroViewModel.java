package com.frgp.remember.ui.Notificaciones.Actividades.Refranero;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RefraneroViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RefraneroViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}