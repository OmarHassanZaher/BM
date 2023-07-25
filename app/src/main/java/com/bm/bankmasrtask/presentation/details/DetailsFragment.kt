package com.bm.bankmasrtask.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bm.bankmasrtask.data.model.response.HistoricalDataResponse
import com.bm.bankmasrtask.databinding.FragmentDetailsBinding
import com.bm.bankmasrtask.domain.entity.Resource
import com.bm.bankmasrtask.presentation.basefragment.BaseFragment
import com.bm.bankmasrtask.presentation.details.adapter.HistoricalDataAdapter
import com.bm.bankmasrtask.presentation.details.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import java.time.LocalDate

@AndroidEntryPoint
class DetailsFragment : BaseFragment() {
    var binding: FragmentDetailsBinding? = null

    @Inject
    lateinit var historicalAdapter: HistoricalDataAdapter

    lateinit var viewModel: DetailsViewModel
    private var from: String? = null
    private var to: String? = null
    private var startDate: LocalDate? = null
    private var endDate: LocalDate? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        from = DetailsFragmentArgs.fromBundle(requireArguments()).from
        to = DetailsFragmentArgs.fromBundle(requireArguments()).to
        endDate = LocalDate.now()
        startDate = LocalDate.now().minusDays(2)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            initialization()
    }
    fun initialization() {
        initViewModel()
        initRV()
        initListeners()
    }
    private fun initListeners() {
        binding!!.convertBtn.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsFragmentToConvertFragmentFragment2()
            findNavController().navigate(action)
        }
        binding!!.backBtn.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }
    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        val endDate = LocalDate.now()
        val startDate = LocalDate.now().minusDays(3)

        viewModel.getHistoricalData(
            accessKey = "e30974634ea8ab00fd58c75b27e4385d",
            fromCurrency = from!!,
            toCurrency = to!!,
            startDate = startDate,
            endDate = endDate
        )
        viewModel.getHistoricalLiveData().observe(viewLifecycleOwner, historicalObserver)



    }
    private fun initRV() {
        binding!!.detailsRv.adapter = historicalAdapter
    }
    private var historicalObserver: Observer<Resource<HistoricalDataResponse?>> = Observer {
        when (it.status) {
            Resource.Status.LOADING -> {
            }
            Resource.Status.SUCCESS -> {
                historicalAdapter.setData(it.data as List<HistoricalDataResponse.Rates?>)
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