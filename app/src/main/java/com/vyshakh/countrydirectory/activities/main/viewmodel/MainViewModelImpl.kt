package com.vyshakh.countrydirectory.activities.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vyshakh.countrydirectory.R
import com.vyshakh.countrydirectory.activities.main.data.CountryListItem
import com.vyshakh.countrydirectory.activities.main.data.IMainRepository
import com.vyshakh.countrydirectory.activities.main.data.InputFormState
import com.vyshakh.countrydirectory.helper.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

interface IMainViewModel {
    val countryListResponse: LiveData<List<CountryListItem>>
    val selectedItem: LiveData<CountryListItem?>
    val inputFormState: LiveData<InputFormState>
    fun getCountryList()
    fun setSelectedItemIndex(index: Int)
    fun countryCodeTextChanged(countryCode: String?)
    fun updateItemAtSelectedIndex(item: CountryListItem?)
}

class MainViewModelImpl @Inject constructor(private val mainRepository: IMainRepository) :
    ViewModel(),
    IMainViewModel {

    private val _countryListResponse = MutableLiveData<List<CountryListItem>>()
    override val countryListResponse: LiveData<List<CountryListItem>> = _countryListResponse

    private val _inputFormState = SingleLiveEvent<InputFormState>()
    override val inputFormState: LiveData<InputFormState> = _inputFormState

    private var _index = MutableLiveData<Int>()

    override val selectedItem: LiveData<CountryListItem?> = switchMap(_index) {
        mainRepository.getItemAtIndex(it)
    }

    override fun getCountryList() {
        viewModelScope.launch(Dispatchers.IO) {
            _countryListResponse.postValue(mainRepository.getCountryList())
        }
    }

    override fun setSelectedItemIndex(index: Int) {
        _index.value = index
    }

    override fun countryCodeTextChanged(countryCode: String?) {
        if (countryCode.isNullOrEmpty()) {
            _inputFormState.value = InputFormState(
                textError = R.string.empty_message,
                isDataValid = false,
                isDataUpdated = false
            )
        } else {
            _inputFormState.value =
                InputFormState(textError = null, isDataValid = true, isDataUpdated = false)
        }
    }

    override fun updateItemAtSelectedIndex(item: CountryListItem?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (item?.validateCountyCode() == true) {
                mainRepository.updateItemAtIndex(_index.value, item)
                _countryListResponse.postValue(mainRepository.getCountryList())
                _index.postValue(_index.value)
                _inputFormState.postValue(
                    InputFormState(
                        textError = null,
                        isDataValid = true,
                        isDataUpdated = true
                    )
                )
            } else {
                _inputFormState.postValue(InputFormState(textError = R.string.empty_message))
            }
        }
    }
}
