package com.bm.bankmasrtask

import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bm.bankmasrtask.data.model.response.HistoricalDataResponse
import com.bm.bankmasrtask.databinding.FragmentDetailsBinding
import com.bm.bankmasrtask.databinding.FragmentHomeBinding
import com.bm.bankmasrtask.domain.entity.Resource
import com.bm.bankmasrtask.presentation.details.DetailsFragment
import com.bm.bankmasrtask.presentation.details.adapter.HistoricalDataAdapter
import com.bm.bankmasrtask.presentation.details.viewmodel.DetailsViewModel
import com.bm.bankmasrtask.presentation.home.HomeFragment
import com.bm.bankmasrtask.presentation.home.HomeFragmentDirections
import com.bm.bankmasrtask.presentation.home.viewmodel.HomeViewModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @RunWith(MockitoJUnitRunner::class)
    class HomeFragmentTest {

        @Mock
        private lateinit var viewModel: HomeViewModel

        private lateinit var fragment: HomeFragment
        private lateinit var binding: FragmentHomeBinding

        @Before
        fun setUp() {
            fragment = HomeFragment()
            fragment.binding = binding
            fragment.viewModel = viewModel
            fragment.isAmountUpdating = false
        }

        @Test
        fun testConversionRateUpdate() {
            val fromInput = binding.fromInput
            val toInput = binding.toInput
            val fromAmount = binding.fromAmount
            val toAmount = binding.toAmount

            // Set up mock data
            val conversionRate = 0.85
            val amount = 10.0
            val fromCurrency = "USD"
            val toCurrency = "EUR"

            `when`(viewModel.getConversionRate(fromCurrency, toCurrency)).thenReturn(conversionRate)

            fromInput.setText(fromCurrency)
            toInput.setText(toCurrency)
            fromAmount.setText(amount.toString())

            fragment.updateConversionRate()

            assertEquals((amount * conversionRate).toString(), toAmount.text.toString())
            @Test
            fun testDetailsButtonNavigation() {
                val navController = mock(NavController::class.java)
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                    from = "USD",
                    to = "EUR"
                )

                `when`(binding.detailsButton.findNavController()).thenReturn(navController)
                binding.detailsButton.performClick()

                verify(navController).navigate(action)
            }
        }
    }
    @RunWith(MockitoJUnitRunner::class)
    class DetailsFragmentTest {

        @Mock
        private lateinit var viewModel: DetailsViewModel

        @Mock
        private lateinit var historicalAdapter: HistoricalDataAdapter

        private lateinit var fragment: DetailsFragment
        private lateinit var binding: FragmentDetailsBinding

        @Before
        fun setUp() {
            fragment = DetailsFragment()
            fragment.binding = binding
            fragment.viewModel = viewModel
            fragment.historicalAdapter = historicalAdapter
        }

        @Test
        fun testHistoricalDataUpdate() {

            val historicalData = createHistoricalDataResponse()

            `when`(viewModel.getHistoricalLiveData()).thenReturn(MutableLiveData(Resource.success(historicalData)))

            fragment.initialization()

            verify(historicalAdapter).setData(historicalData.rates.keys.toList())
        }

        private fun createHistoricalDataResponse(): HistoricalDataResponse {
            return HistoricalDataResponse(
                success = true,
                timestamp = 0,
                base = "USD",
                date = "",
                rates = HistoricalDataResponse.Rates(
                    mapOf(
                        "2023-07-21" to 0.85,
                        "2023-07-22" to 0.84,
                        "2023-07-23" to 0.83
                    )
                )
            )
        }
    }
}


