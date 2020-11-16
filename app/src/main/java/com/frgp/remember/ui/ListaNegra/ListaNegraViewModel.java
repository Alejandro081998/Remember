package com.frgp.remember.ui.ListaNegra;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListaNegraViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListaNegraViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}