package com.example.pd.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pd.main.network.SignalApi
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.Utils.init
import kotlinx.coroutines.launch

class ApiViewModel: ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _signals = ArrayList<Entry>()
    val signals: ArrayList<Entry> = _signals

    private val _data = MutableLiveData<LineData>()
    val data: LiveData<LineData> = _data

    init {
        getSignalApi()
    }

    private fun getSignalApi() {
        viewModelScope.launch {
            try {
                val listResult = SignalApi.retrofitService.getSignal()
                _status.value = "Success: ${listResult.size} Signals retrieved"

                for (signal in listResult) {
                    _signals.add(Entry(signal.id.toFloat(), signal.data))
                }

                val lineDataset = LineDataSet(_signals, "Signal")
                lineDataset.circleRadius = 2f
                lineDataset.valueTextSize = 10F
                lineDataset.mode = LineDataSet.Mode.CUBIC_BEZIER
                _data.value = LineData(lineDataset)
            }
            catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}