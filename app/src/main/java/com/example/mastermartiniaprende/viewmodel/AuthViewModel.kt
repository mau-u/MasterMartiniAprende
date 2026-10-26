package com.example.mastermartiniaprende.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mastermartiniaprende.model.FakeDatabase
import com.example.mastermartiniaprende.model.Usuario

class AuthViewModel : ViewModel() {

    var mensaje = mutableStateOf("")
    var usuarioActual = mutableStateOf<String?>(null)

    fun registrar(
        nombre: String,
        email: String,
        password: String,
        tipoNegocio: String = ""
    ) {
        val nuevo = Usuario(
            nombre = nombre,
            email = email,
            password = password,
            tipoNegocio = tipoNegocio
        )

        if (FakeDatabase.registrar(nuevo)) {
            mensaje.value = "Registro exitoso"
        } else {
            mensaje.value = "El usuario ya existe"
        }
    }

    fun login(email: String, password: String): Boolean {
        return if (FakeDatabase.login(email, password)) {
            usuarioActual.value = email
            mensaje.value = "Inicio de sesión exitoso"
            true
        } else {
            mensaje.value = "Credenciales inválidas"
            false
        }
    }
}