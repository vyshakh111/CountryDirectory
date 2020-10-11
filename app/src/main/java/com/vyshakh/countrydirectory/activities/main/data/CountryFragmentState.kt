package com.vyshakh.countrydirectory.activities.main.data

data class CountryFragmentState(var addCountryFragmentStateValue: CountryFragmentStateValue)

enum class CountryFragmentStateValue {
    CountryListFragment, CountryDetailsFragment
}