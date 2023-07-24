package com.bm.bankmasrtask.presentation.convert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bm.bankmasrtask.data.model.response.ConversionResponse
import com.bm.bankmasrtask.databinding.FragmentConvertFragmentBinding
import com.bm.bankmasrtask.domain.entity.Resource
import com.bm.bankmasrtask.presentation.basefragment.BaseFragment
import com.bm.bankmasrtask.presentation.convert.adapter.ConversionRatesAdapter
import com.bm.bankmasrtask.presentation.convert.viewmodel.ConvertViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class ConvertFragmentFragment : BaseFragment() {
    private var binding: FragmentConvertFragmentBinding? = null
    private lateinit var viewModel: ConvertViewModel
    private var baseCurrency = "USD"
    private val currencies = listOf("EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "HKD", "NZD", "SEK")
    private val amount = 100
    @Inject
    lateinit var adapter : ConversionRatesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConvertFragmentBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }
    private fun initialization() {
        initViewModel()
        initRV()
        initListeners()
    }

    private fun initListeners() {
        binding!!.backBtn.setOnClickListener {
           findNavController().navigateUp()
        }
    }

    private fun initRV() {
        binding!!.conversionRv.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ConvertViewModel::class.java]
        viewModel.getConvertData(accessKey = "e30974634ea8ab00fd58c75b27e4385d", fromCurrency = baseCurrency, toCurrency = currencies.joinToString(separator = ",") , amount = amount.toDouble())
        viewModel.getConvertLiveData().observe(viewLifecycleOwner,convertObserver)
    }
    private var convertObserver: Observer<Resource<ConversionResponse?>> = Observer {
        when (it.status) {
            Resource.Status.LOADING -> {
            }
            Resource.Status.SUCCESS -> {

            }
            Resource.Status.API_ERROR -> {
                handleError(it.error_msg.toString())
            }
            Resource.Status.DOMAIN_ERROR -> {
                handleError(it.throwable.toString())
            }
        }
    }
}