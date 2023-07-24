package com.bm.bankmasrtask.domain.usecase.convert

import com.bm.bankmasrtask.data.model.response.ConversionResponse
import com.bm.bankmasrtask.domain.entity.Resource
import com.bm.bankmasrtask.domain.mapper.ResourceMapper
import com.bm.bankmasrtask.util.ApiServices
import javax.inject.Inject

class ConvertUseCase @Inject constructor(
    private val apiServices: ApiServices,
    private val mapper: ResourceMapper<ConversionResponse?>
) {
    suspend fun execute(accessKey: String,fromCurrency: String,toCurrency: String,amount: Double): Resource<ConversionResponse?> = mapper.map(apiServices.convertCurrency(accessKey,fromCurrency,toCurrency,amount))
}