package com.bm.bankmasrtask.presentation.convert.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.bankmasrtask.data.model.response.ConversionResponse
import com.bm.bankmasrtask.databinding.ConvertItemBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ConversionRatesAdapter @Inject constructor(
    @ApplicationContext val context: Context,
) : RecyclerView.Adapter<ConversionRatesAdapter.ViewHolder>() {

    private lateinit var binding: ConvertItemBinding
    private val convertList: MutableList<ConversionResponse.Rates?> = mutableListOf()
    private var popularCurrencies: List<String> = emptyList()
    private var convertedRates: List<Double> = emptyList()

    inner class ViewHolder(private val binding: ConvertItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ConversionResponse.Rates?) {
            binding.apply {
                currencyCodeTv.text = item?.currency
                convertedRateTv.text = item?.rate.toString()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConversionRatesAdapter.ViewHolder {
        binding = ConvertItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConversionRatesAdapter.ViewHolder, position: Int) {
        holder.bind(convertList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return convertList.size
    }

    fun setData(
        list: List<ConversionResponse.Rates?>,
        popularCurrencies: List<String>,
        convertedRates: List<Double>
    ) {
        convertList.clear()
        convertList.addAll(list)
        this.popularCurrencies = popularCurrencies
        this.convertedRates = convertedRates
        notifyDataSetChanged()
    }

    fun getPopularCurrencies(popularCurrencies: List<String>): List<String> {
        return this.popularCurrencies
    }

    fun getConvertedRates(convertedRates: MutableList<Double>): List<Double> {
        return this.convertedRates
    }

}