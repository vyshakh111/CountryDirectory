package com.vyshakh.countrydirectory.activities.main.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vyshakh.countrydirectory.MyApplication
import com.vyshakh.countrydirectory.helper.getJsonString
import com.vyshakh.countrydirectory.helper.getObjectFromJson
import javax.inject.Inject
import javax.inject.Singleton

interface IMainRepository {
    var countryList: ArrayList<CountryListItem>?
    fun getCountryJson(): String
    fun getCountryList(): List<CountryListItem>?
    fun getItemAtIndex(index: Int): LiveData<CountryListItem?>
    fun updateItemAtIndex(index: Int?, countryListItem: CountryListItem?)
}

@Singleton
class MainRepositoryImpl @Inject constructor(private val context: MyApplication) : IMainRepository {

    override var countryList: ArrayList<CountryListItem>? = null

    override fun getCountryJson(): String {
        return getJsonString(context,"country.json")
    }

    override fun getCountryList(): List<CountryListItem>? {
        if (countryList == null)
            countryList = getObjectFromJson<CountryListResponse>(
                getCountryJson()
            ).list
        return countryList
    }

    override fun getItemAtIndex(index: Int): LiveData<CountryListItem?> {
        val countryListItemSource = MutableLiveData<CountryListItem?>()
        countryListItemSource.postValue(countryList?.get(index))
        return countryListItemSource
    }

    override fun updateItemAtIndex(index: Int?, countryListItem: CountryListItem?) {
        index?.let {
            countryListItem?.let {
                countryList?.set(index, countryListItem)
            }
        }
    }
}