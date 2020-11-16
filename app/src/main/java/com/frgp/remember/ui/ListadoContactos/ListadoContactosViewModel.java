package com.frgp.remember.ui.ListadoContactos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListadoContactosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListadoContactosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}