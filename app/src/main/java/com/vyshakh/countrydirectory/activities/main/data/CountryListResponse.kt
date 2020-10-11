package com.vyshakh.countrydirectory.activities.main.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CountryListResponse(
    @SerializedName("serversList")
    @Expose
    var list: ArrayList<CountryListItem>
)

data class CountryListItem(
    @SerializedName("baseurl")
    @Expose
    var url: String,

    @SerializedName("country")
    @Expose
    var countryName: String,

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("desc")
    @Expose
    var description: String,

    var countryCode: String? = ""
) {
    fun validateCountyCode(): Boolean = countryCode?.isNotEmpty() ?: false
}

