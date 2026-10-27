package com.example.mastermartiniaprende.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mastermartiniaprende.model.Usuario
import com.example.mastermartiniaprende.repository.AppRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AppRepository
) : ViewModel() {

    var mensaje = mutableStateOf("")
    var usuarioActual = mutableStateOf<String?>(null)
    var perfilActual = mutableStateOf<Usuario?>(null)
    var cargando = mutableStateOf(false)

    var errorNombreRegistro = mutableStateOf<String?>(null)
    var errorEmailRegistro = mutableStateOf<String?>(null)
    var errorTipoNegocioRegistro = mutableStateOf<String?>(null)
    var errorPasswordRegistro = mutableStateOf<String?>(null)

    var errorEmailLogin = mutableStateOf<String?>(null)
    var errorPasswordLogin = mutableStateOf<String?>(null)

    // Validaciones del formulario de registro
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

    // Registrar un usuario
    fun registrar(
        nombre: String,
        email: String,
        password: String,
        tipoNegocio: String,
        onResultado: (Boolean) -> Unit
    ) {
        mensaje.value = ""

        if (!validarRegistro(nombre, email, password, tipoNegocio)) {
            mensaje.value = "Revisa los campos marcados."
            onResultado(false)
            return
        }

        viewModelScope.launch {
            cargando.value = true

            try {
                val registrado = repository.registrarUsuario(
                    nombre = nombre,
                    email = email,
                    password = password,
                    tipoNegocio = tipoNegocio
                )

                mensaje.value = if (registrado) {
                    "Registro exitoso"
                } else {
                    "El usuario ya existe"
                }

                onResultado(registrado)

            } catch (error: Exception) {
                mensaje.value =
                    "No se pudo guardar el registro. Intenta nuevamente."

                onResultado(false)

            } finally {
                cargando.value = false
            }
        }
    }

    // Inicio de sesión con validación de los campos
    fun login(
        email: String,
        password: String,
        onResultado: (Usuario?) -> Unit
    ) {
        mensaje.value = ""

        errorEmailLogin.value = when {
            email.isBlank() ->
                "Ingresa tu correo electrónico."

            !android.util.Patterns.EMAIL_ADDRESS
                .matcher(email.trim()).matches() ->
                "Ingresa un correo válido."

            else -> null
        }

        errorPasswordLogin.value =
            if (password.isBlank()) {
                "Ingresa tu contraseña."
            } else {
                null
            }

        if (
            errorEmailLogin.value != null ||
            errorPasswordLogin.value != null
        ) {
            onResultado(null)
            return
        }

        viewModelScope.launch {
            cargando.value = true

            try {
                val usuario = repository.iniciarSesion(email, password)

                if (usuario != null) {
                    usuarioActual.value = usuario.email
                    perfilActual.value = usuario
                    mensaje.value = "Inicio de sesión exitoso"
                } else {
                    mensaje.value = "Correo o contraseña incorrectos."
                }

                onResultado(usuario)

            } catch (error: Exception) {
                mensaje.value =
                    "No se pudo iniciar sesión. Intenta nuevamente."

                onResultado(null)

            } finally {
                cargando.value = false
            }
        }
    }

    // Recuperar el usuario para restaurar una sesión existente
    fun restaurarSesion(
        email: String,
        onResultado: (Usuario?) -> Unit
    ) {
        viewModelScope.launch {
            cargando.value = true

            // CORRECCIÓN: declarar explícitamente el tipo
            val usuario: Usuario? = try {
                repository.obtenerUsuarioPorEmail(email)

            } catch (error: Exception) {
                mensaje.value = "No se pudo recuperar la sesión."
                null

            } finally {
                cargando.value = false
            }

            usuarioActual.value = usuario?.email
            perfilActual.value = usuario

            onResultado(usuario)
        }
    }

    // Cerrar sesión sin eliminar la cuenta guardada en Room
    fun cerrarSesion() {
        usuarioActual.value = null
        perfilActual.value = null
        mensaje.value = ""
    }
}