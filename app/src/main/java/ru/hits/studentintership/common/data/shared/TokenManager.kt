package ru.hits.studentintership.common.data.shared

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(
    @ApplicationContext context: Context,
) {
    val SHARED_PREFS = "SHARED_PREFS"
    val USER_TOKEN = "token"
    val USER_EMAIL = "email"
    val USER_PASSWORD = "password"
    val PHOTO_URI = "photo"
    private var prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    fun saveToken(token: String?) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? =
        prefs.getString(USER_TOKEN, null)

    fun saveEmail(email: String) {
        val editor = prefs.edit()
        editor.putString(USER_EMAIL, email)
        editor.apply()
    }

    fun getEmail(): String? =
        prefs.getString(USER_EMAIL, null)

    fun savePassword(password: String) {
        val editor = prefs.edit()
        editor.putString(USER_PASSWORD, password)
        editor.apply()
    }

    fun getPassword(): String? =
        prefs.getString(USER_PASSWORD, null)

    fun saveUri(uri: String) {
        val editor = prefs.edit()
        editor.putString(PHOTO_URI, uri)
        editor.apply()
    }

    fun getUri(): String? =
        prefs.getString(PHOTO_URI, null)
}