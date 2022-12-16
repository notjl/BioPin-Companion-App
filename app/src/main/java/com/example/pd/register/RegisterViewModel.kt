package com.example.pd.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pd.database.dao.UserDao
import com.example.pd.database.models.User

class RegisterViewModel(private val userDao: UserDao): ViewModel() {
    private fun insertUser(user: User) {
        userDao.insert(user)
    }

    private fun getNewUserEntry(username: String, password: String, firstName: String, surname: String): User {
        return User(
            firstName = firstName,
            surname = surname,
            username = username,
            password = password
        )
    }

    fun addNewUser(username: String, password: String, firstName: String, surname: String) {
        val newUser = getNewUserEntry(username, password, firstName, surname)
        insertUser(newUser)
    }

    fun isEntryValid(username: String, firstName: String, surname: String, password: String, confirmPassword: String): Boolean {
        if (username.isBlank() || password.isBlank() || confirmPassword.isBlank() || firstName.isBlank() || surname.isBlank())
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