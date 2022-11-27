package com.ncsmatias.calculator.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel  extends ViewModel {
    private MutableLiveData<String> expression;
    private MutableLiveData<String> result;

    public MutableLiveData<String> getCurrentExpression() {
        if (this.expression == null) {
            this.expression = new MutableLiveData<String>();
        }
        return this.expression;
    }

    public MutableLiveData<String> getCurrentResult() {
        if (this.result == null) {
            this.result = new MutableLiveData<String>();
        }
        return this.result;
    }

}
