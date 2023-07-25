package com.bm.bankmasrtask.presentation.details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.bankmasrtask.data.model.response.HistoricalDataResponse
import com.bm.bankmasrtask.databinding.DetailsItemBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class HistoricalDataAdapter @Inject constructor(
    @ApplicationContext val context: Context,
    private val from: String?,
    private val to: String?
) : RecyclerView.Adapter<HistoricalDataAdapter.ViewHolder>() {

    private lateinit var binding: DetailsItemBinding
    private var ratesList: List<HistoricalDataResponse.Rates?> = emptyList()

    inner class ViewHolder(private val binding: DetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HistoricalDataResponse.Rates?) {
            if (item != null) {
                val fromRate = item.rates.get(from ?: "")
                val toRate = item.rates.get(to ?: "")
                if (fromRate != null && toRate != null) {
                    binding.rateTv.text = "${toRate} $to = ${fromRate} $from"
                } else {
                    binding.rateTv.text = "N/A"
                }
            } else {
                binding.rateTv.text = "N/A"
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoricalDataAdapter.ViewHolder {
        binding = DetailsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoricalDataAdapter.ViewHolder, position: Int) {
        holder.bind(ratesList.getOrNull(position))
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return ratesList.size
    }

    fun setData(list: List<HistoricalDataResponse.Rates?>) {
        ratesList = list
        notifyDataSetChanged()
    }
}