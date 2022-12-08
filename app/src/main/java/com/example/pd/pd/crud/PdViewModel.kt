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

    private fun updateSignal(signal: Signal){
        signalDao.update(signal)
    }

    private fun getNewSignalEntry(signal: Int, type: String): Signal {
        return Signal(
            signal = signal,
            type = type
        )
    }

    private fun getUpdatedSignalEntry(
        id: Int,
        signal: Int,
        type: String
    ): Signal {
        return Signal(
            id = id,
            signal = signal,
            type = type
        )
    }

    fun addNewSignal(signal: Int, type: String) {
        val newSignal = getNewSignalEntry(signal, type)
        insertSignal(newSignal)
    }

    fun updateExistingSignal(
        id: Int,
        signal: Int,
        type: String
    ) {
        val updatedSignal: Signal = getUpdatedSignalEntry(id, signal, type)
        updateSignal(updatedSignal)
    }

    fun isEntryValid(signal: String): Boolean {
        if (signal.isBlank())
            return false
        return true
    }

    fun getAllSignal(): Flow<List<Signal>> = signalDao.getAll()

    fun getSignal(id: Int): Flow<Signal> = signalDao.getSignal(id)

    fun deleteSignal(signal: Signal) {
        signalDao.delete(signal)
    }
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