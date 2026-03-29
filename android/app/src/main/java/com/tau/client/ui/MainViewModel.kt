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
    val host = repository.tauHost.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "100.91.199.107")
    val port = repository.tauPort.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 3001)
    val token = repository.tauToken.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "fjrtsale-secure-2026")

    private val _messages = MutableStateFlow<List<String>>(emptyList())
    val messages: StateFlow<List<String>> = _messages

    fun connect(host: String, port: Int, token: String) {
        viewModelScope.launch {
            repository.saveSettings(host, port, token)
            client.connect(host, port, token = token)
        }
    }

    fun sendMessage(content: String) {
        _messages.value += "You: $content"
        // TODO: client.send(content)
    }
}
