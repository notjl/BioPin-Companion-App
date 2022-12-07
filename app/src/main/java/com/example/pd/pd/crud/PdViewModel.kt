package com.example.pd.pd.crudmo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pd.database.dao.SignalDao
import com.example.pd.database.models.Signal
import kotlinx.coroutines.flow.Flow

class PdViewModel(private val signalDao: SignalDao): ViewModel() {
    private fun insertSignal(signal: Signal) {
        signalDao.insert(signal)
    }

    private fun getNewSignalEntry(signal: Int, type: String): Signal {
        return Signal(
            signal = signal,
            type = type
        )
    }

    fun addNewSignal(signal: Int, type: String) {
        val newSignal = getNewSignalEntry(signal, type)
        insertSignal(newSignal)
    }

    fun isEntryValid(signal: String): Boolean {
        if (signal.isBlank())
            return false
        return true
    }

    fun getAllSignal(): Flow<List<Signal>> = signalDao.getAll()
}

class PdViewModelFactory(private val signalDao: SignalDao): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PdViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PdViewModel(signalDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}