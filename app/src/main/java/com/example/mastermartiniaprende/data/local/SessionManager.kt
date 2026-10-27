package com.example.mastermartiniaprende.data.local

import android.content.Context

class SessionManager(context: Context) {

    private val preferences = context
        .applicationContext
        .getSharedPreferences(
            "master_martini_session",
            Context.MODE_PRIVATE
        )

    fun guardarSesion(email: String) {
        preferences.edit()
            .putString("usuario_email", email.trim())
            .apply()
    }

    fun obtenerEmailSesion(): String? {
        return preferences.getString("usuario_email", null)
    }

    fun haySesionActiva(): Boolean {
        return !obtenerEmailSesion().isNullOrBlank()
    }

    fun cerrarSesion() {
        preferences.edit()
            .remove("usuario_email")
            .apply()
    }
}