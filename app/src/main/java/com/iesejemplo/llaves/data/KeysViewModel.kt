package com.iesejemplo.llaves.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/** Estado en memoria de toda la app (datos de ejemplo). */
class KeysViewModel : ViewModel() {

    // ---- Login ----
    var loggedIn by mutableStateOf(false)
        private set
    var loginError by mutableStateOf<String?>(null)
        private set

    fun login(user: String, pass: String) {
        when {
            user.isBlank() || pass.isBlank() -> loginError = "Rellena usuario y contraseña"
            !user.contains("@") -> loginError = "El usuario debe ser un correo válido"
            pass.length < 4 -> loginError = "La contraseña es demasiado corta"
            else -> { loginError = null; loggedIn = true }
        }
    }

    fun logout() { loggedIn = false }

    // ---- Llaves ----
    val keys = mutableStateListOf(
        KeyItem("101", "Aula 101", KeyStatus.DISPONIBLE, "04A1B2C3"),
        KeyItem("102", "Aula 102", KeyStatus.PRESTADA, "04A1B2C4"),
        KeyItem("LAB", "Laboratorio", KeyStatus.DISPONIBLE, "04A1B2C5"),
        KeyItem("GIM", "Gimnasio", KeyStatus.PRESTADA, "04A1B2C6"),
        KeyItem("BIB", "Biblioteca", KeyStatus.DISPONIBLE, "04A1B2C7"),
        KeyItem("SP", "Sala profesores", KeyStatus.DISPONIBLE, "04A1B2C8"),
        KeyItem("201", "Aula 201", KeyStatus.PRESTADA, "04A1B2C9"),
        KeyItem("TAL", "Taller", KeyStatus.DISPONIBLE, "04A1B2D0"),
    )

    val available: Int get() = keys.count { it.status == KeyStatus.DISPONIBLE }
    val borrowed: Int get() = keys.count { it.status == KeyStatus.PRESTADA }

    // ---- Préstamos ----
    val loans = mutableStateListOf(
        Loan("102", "Llave Aula 102", "Marta Gómez", "Desde las 08:15"),
        Loan("GIM", "Llave Gimnasio", "Luis Fernández", "Desde las 09:00"),
        Loan("201", "Llave Aula 201", "Carmen Ruiz", "Desde ayer, 17:30", overdue = true),
    )

    fun returnKey(code: String) {
        loans.removeAll { it.keyCode == code }
        setStatus(code, KeyStatus.DISPONIBLE)
    }

    private fun setStatus(code: String, status: KeyStatus) {
        val i = keys.indexOfFirst { it.code == code }
        if (i >= 0) keys[i] = keys[i].copy(status = status)
    }

    // ---- NFC / búsqueda ----
    var searchQuery by mutableStateOf("")
    var nfcMessage by mutableStateOf<String?>(null)

    fun searchResults(): List<KeyItem> {
        val q = searchQuery.trim()
        if (q.isEmpty()) return emptyList()
        return keys.filter {
            it.name.contains(q, ignoreCase = true) || it.code.contains(q, ignoreCase = true)
        }
    }

    /** Se llama cuando la lectura NFC detecta una etiqueta. */
    fun onTagRead(tagId: String) {
        val key = keys.firstOrNull { it.nfcTagId.equals(tagId, ignoreCase = true) }
        nfcMessage = if (key == null) {
            "Etiqueta $tagId no registrada"
        } else {
            if (key.status == KeyStatus.PRESTADA) {
                returnKey(key.code)
                "Llave ${key.name} devuelta"
            } else {
                setStatus(key.code, KeyStatus.PRESTADA)
                loans.add(Loan(key.code, "Llave ${key.name}", "Profesor/a (tú)", "Desde ahora"))
                "Llave ${key.name} prestada"
            }
        }
    }

    fun clearNfcMessage() { nfcMessage = null }

    // ---- Incidencias ----
    val incidents = mutableStateListOf<Incident>()

    fun sendIncident(keyName: String, type: IncidentType, description: String): Boolean {
        if (keyName.isBlank() || description.isBlank()) return false
        incidents.add(Incident(keyName, type, description))
        return true
    }
}
