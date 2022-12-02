package com.example.pd.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.midterms.database.dao.UserDao
import com.example.midterms.database.models.User

class RegisterViewModel(private val userDao: UserDao): ViewModel() {
    private fun insertUser(user: User) {
        userDao.insert(user)
    }

    private fun getNewUserEntry(username: String, password: String): User {
        return User(
            username = username,
            password = password
        )
    }

    fun addNewUser(username: String, password: String) {
        val newUser = getNewUserEntry(username, password)
        insertUser(newUser)
    }

    fun isEntryValid(username: String, password: String, confirmPassword: String): Boolean {
        if (username.isBlank() || password.isBlank() || confirmPassword.isBlank())
            return false
        return true
    }

    fun isPasswordSimilar(password: String, confirmPassword: String): Boolean {
        if (password == confirmPassword)
            return true
        return false
    }
}

class RegisterViewModelFactory(private val userDao: UserDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}