package com.example.pd.main

import android.util.Log
import androidx.lifecycle.*
import com.example.pd.database.dao.SignalDao
import com.example.pd.database.models.Signal
import kotlinx.coroutines.flow.Flow

class PdViewModel(private val signalDao: SignalDao): ViewModel() {

    private val _hertz = MutableLiveData<Int>()
    val hertz: LiveData<Int> = _hertz

    private val _type = MutableLiveData<String>()
    val type: LiveData<String> = _type

    private val _direction = MutableLiveData<String>()
    val direction: LiveData<String> = _direction

    fun setHertz(signalHertz: Int) {
        _hertz.value = signalHertz
        Log.d("setHertz", hertz.value.toString())
    }

    fun setType(signalType: String) {
        _type.value = signalType
        Log.d("setType", type.value.toString())
    }

    fun setDirection(signalDirection: String) {
        _direction.value = signalDirection
        Log.d("setDirection", direction.value.toString())
    }

    private fun insertSignal(signal: Signal) {
        signalDao.insert(signal)
    }

    private fun updateSignal(signal: Signal){
        signalDao.update(signal)
    }

    private fun getNewSignalEntry(signal: Int, type: String, direction: String): Signal {
        return Signal(
            signal = signal,
            type = type,
            direction = direction
        )
    }

    private fun getUpdatedSignalEntry(
        id: Int,
        signal: Int,
        type: String,
        direction: String
    ): Signal {
        return Signal(
            id = id,
            signal = signal,
            type = type,
            direction = direction
        )
    }

    fun addNewSignal(signal: Int, type: String, direction: String) {
        val newSignal = getNewSignalEntry(signal, type, direction)
        insertSignal(newSignal)
    }

    fun updateExistingSignal(
        id: Int,
        signal: Int,
        type: String,
        direction: String,
    ) {
        val updatedSignal: Signal = getUpdatedSignalEntry(id, signal, type, direction)
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