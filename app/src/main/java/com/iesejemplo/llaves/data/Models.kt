package com.iesejemplo.llaves.data

enum class KeyStatus { DISPONIBLE, PRESTADA }

data class KeyItem(
    val code: String,          // 101, LAB...
    val name: String,          // Aula 101
    val status: KeyStatus,
    val nfcTagId: String? = null  // UID de la etiqueta NFC (hex)
)

data class Loan(
    val keyCode: String,
    val keyName: String,       // "Llave Aula 102"
    val person: String,
    val since: String,         // "Desde las 08:15"
    val overdue: Boolean = false
)

enum class IncidentType { PERDIDA, ROTA }

data class Incident(
    val keyName: String,
    val type: IncidentType,
    val description: String
)
