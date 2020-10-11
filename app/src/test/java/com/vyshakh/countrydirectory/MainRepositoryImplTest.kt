package com.vyshakh.countrydirectory

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vyshakh.countrydirectory.activities.main.data.CountryListItem
import com.vyshakh.countrydirectory.activities.main.data.IMainRepository
import com.vyshakh.countrydirectory.activities.main.viewmodel.IMainViewModel
import com.vyshakh.countrydirectory.helper.getJsonString
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainRepositoryImplTest {

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var VM : IMainViewModel


    private var countryList : ArrayList<CountryListItem> = ArrayList()
    private lateinit var  countryListItem : CountryListItem
    private val countryCode="91"
    private var countryListItemSource = MutableLiveData<List<CountryListItem>>()
    private var selectedIndex : Int = 0

    @Before
    fun setup() {
        countryListItem = CountryListItem("https://hotmail.com/th","Thailand","TH","Thailand Hotmail",countryCode)
        countryList.add(countryListItem)
        selectedIndex = 1
    }


    @Test
    fun setSelectedIndex(){
        `when`(VM.setSelectedItemIndex(selectedIndex)).then{
            selectedIndex
        }
        VM.setSelectedItemIndex(1)
        assertThat(selectedIndex,`is`(1))
    }

}