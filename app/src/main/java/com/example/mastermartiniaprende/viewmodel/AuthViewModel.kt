package com.example.mastermartiniaprende.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mastermartiniaprende.model.FakeDatabase
import com.example.mastermartiniaprende.model.Usuario

class AuthViewModel : ViewModel() {

    var mensaje = mutableStateOf("")
    var usuarioActual = mutableStateOf<String?>(null)

    // Errores del formulario de registro
    var errorNombreRegistro = mutableStateOf<String?>(null)
    var errorEmailRegistro = mutableStateOf<String?>(null)
    var errorTipoNegocioRegistro = mutableStateOf<String?>(null)
    var errorPasswordRegistro = mutableStateOf<String?>(null)

    // Errores del formulario de inicio de sesión
    var errorEmailLogin = mutableStateOf<String?>(null)
    var errorPasswordLogin = mutableStateOf<String?>(null)


    // VALIDACIONES DEL REGISTRO
    fun validarRegistro(
        nombre: String,
        email: String,
        password: String,
        tipoNegocio: String
    ): Boolean {

        errorNombreRegistro.value = when {
            nombre.isBlank() ->
                "Ingresa tu nombre."

            nombre.trim().length < 2 ->
                "El nombre debe tener al menos 2 caracteres."

            else -> null
        }

        errorEmailRegistro.value = when {
            email.isBlank() ->
                "Ingresa tu correo electrónico."

            !android.util.Patterns.EMAIL_ADDRESS
                .matcher(email.trim()).matches() ->
                "Ingresa un correo válido."

            else -> null
        }

        errorTipoNegocioRegistro.value =
            if (tipoNegocio.isBlank()) {
                "Ingresa tu tipo de negocio."
            } else {
                null
            }

        errorPasswordRegistro.value =
            if (password.length < 8) {
                "La contraseña debe tener al menos 8 caracteres."
            } else {
                null
            }

        return errorNombreRegistro.value == null &&
                errorEmailRegistro.value == null &&
                errorTipoNegocioRegistro.value == null &&
                errorPasswordRegistro.value == null
    }


    // REGISTRAR USUARIO
    fun registrar(
        nombre: String,
        email: String,
        password: String,
        tipoNegocio: String = ""
    ): Boolean {

        mensaje.value = ""

        // Validar antes de registrar
        if (!validarRegistro(nombre, email, password, tipoNegocio)) {
            return false
        }

        val nuevo = Usuario(
            nombre = nombre.trim(),
            email = email.trim(),
            password = password,
            tipoNegocio = tipoNegocio.trim()
        )

        val registrado = FakeDatabase.registrar(nuevo)

        mensaje.value = if (registrado) {
            "Registro exitoso"
        } else {
            "El usuario ya existe"
        }

        return registrado
    }


    // INICIO DE SESIÓN
    fun login(
        email: String,
        password: String
    ): Boolean {

        mensaje.value = ""

        // Validar el correo
        errorEmailLogin.value = when {
            email.isBlank() ->
                "Ingresa tu correo electrónico."

            !android.util.Patterns.EMAIL_ADDRESS
                .matcher(email.trim()).matches() ->
                "Ingresa un correo válido."

            else -> null
        }

        // Validar la contraseña
        errorPasswordLogin.value =
            if (password.isBlank()) {
                "Ingresa tu contraseña."
            } else {
                null
            }

        // Si hay errores, no comprobar las credenciales
        if (
            errorEmailLogin.value != null ||
            errorPasswordLogin.value != null
        ) {
            return false
        }

        // Comprobar las credenciales
        return if (
            FakeDatabase.login(email.trim(), password)
        ) {
            usuarioActual.value = email.trim()
            mensaje.value = "Inicio de sesión exitoso"
            true
        } else {
            mensaje.value = "Correo o contraseña incorrectos."
            false
        }
    }
}