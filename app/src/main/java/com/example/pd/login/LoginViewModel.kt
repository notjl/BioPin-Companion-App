package com.example.pd.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pd.database.dao.UserDao
import com.example.pd.database.models.User

class LoginViewModel(private val userDao: UserDao): ViewModel() {
    private fun getUser(username: String, password: String): User? = userDao.get(username, password)

    fun userExist(username: String, password: String): Boolean {
        val user: User? = getUser(username, password)
        if (user != null)
            return true
        return false
    }

    fun isEntryValid(username: String, password: String): Boolean {
        if (username.isBlank() || password.isBlank())
            return false
        return true
    }
}

class LoginViewModelFactory(private val userDao: UserDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}