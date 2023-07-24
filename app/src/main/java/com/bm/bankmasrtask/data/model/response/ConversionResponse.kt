package com.bm.bankmasrtask.data.model.response

import com.google.gson.annotations.SerializedName

data class ConversionResponse(
    @SerializedName("success")
    val success: Boolean? = null,
    @SerializedName("timestamp")
    val timestamp: Long? = null,
    @SerializedName("base")
    val base: String? = null,
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("rates")
    val rates: Rates? = null,
    var convertedRates: List<Double>? = null
) {

    data class Rates(
        @SerializedName("AUD")
        val aud: Double? = null,
        @SerializedName("CAD")
        val cad: Double? = null,
        @SerializedName("MXN")
        val mxn: Double? = null,
        @SerializedName("PLN")
        val pln: Double? = null,
        @SerializedName("USD")
        val usd: Double? = null
    )
}