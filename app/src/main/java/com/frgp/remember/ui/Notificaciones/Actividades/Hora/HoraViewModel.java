package com.frgp.remember.ui.Notificaciones.Actividades.Hora;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HoraViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HoraViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}