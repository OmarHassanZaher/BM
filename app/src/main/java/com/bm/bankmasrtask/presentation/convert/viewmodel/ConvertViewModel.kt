package com.bm.bankmasrtask.presentation.convert.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bm.bankmasrtask.data.model.response.ConversionResponse
import com.bm.bankmasrtask.domain.entity.Resource
import com.bm.bankmasrtask.domain.usecase.convert.ConvertUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConvertViewModel @Inject constructor(
    private val mConvertUseCase: ConvertUseCase
) : ViewModel() {

    private val convertLiveData: MutableLiveData<Resource<ConversionResponse?>> = MutableLiveData()

    fun getConvertData(accessKey: String,fromCurrency: String,toCurrency: String,amount: Double) = viewModelScope.launch(
        Dispatchers.IO) {
        try {
            convertLiveData.postValue((Resource.loading()))
            val result = mConvertUseCase.execute(accessKey, fromCurrency, toCurrency, amount)
            convertLiveData.postValue(result)
        } catch (e: Exception) {
            convertLiveData.postValue(Resource.domainError(e))
        }
    }

    fun getConvertLiveData(): LiveData<Resource<ConversionResponse?>> = convertLiveData
}