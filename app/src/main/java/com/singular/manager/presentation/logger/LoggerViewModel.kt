package com.singular.manager.presentation.logger

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LogMessage(
    val timestamp: Long = System.currentTimeMillis(),
    val level: LogLevel,
    val message: String
)

enum class LogLevel {
    SEND, DEBUG, ERROR
}

class LoggerViewModel : ViewModel() {

    private val _logMessages = MutableStateFlow<List<LogMessage>>(emptyList())
    val logMessages: StateFlow<List<LogMessage>> = _logMessages.asStateFlow()

    fun addLog(level: LogLevel, message: String) {
        _logMessages.update { currentMessages ->
            currentMessages + LogMessage(level = level, message = message)
        }
    }

    fun clearLogs() {
        _logMessages.update { emptyList() }
    }
}
