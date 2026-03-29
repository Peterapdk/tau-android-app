package com.tau.client.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tau.client.data.SettingsRepository
import com.tau.client.network.TauGatewayClient
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: SettingsRepository,
    private val client: TauGatewayClient
) : ViewModel() {

    val connectionStatus = client.connectionState
    val host = repository.tauHost.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")
    val token = repository.tauToken.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    private val _messages = MutableStateFlow<List<String>>(emptyList())
    val messages: StateFlow<List<String>> = _messages

    fun connect(host: String, token: String) {
        viewModelScope.launch {
            repository.saveSettings(host, token)
            client.connect(host, token = token)
        }
    }

    fun sendMessage(content: String) {
        // Implementation for sending via client
        _messages.value += "You: $content"
        // client.send(content)
    }
}
