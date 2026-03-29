package com.tau.client.network

import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive

class TauGatewayClient(private val client: HttpClient) {
    private val _connectionState = MutableStateFlow<ConnectionStatus>(ConnectionStatus.Disconnected)
    val connectionState: StateFlow<ConnectionStatus> = _connectionState

    suspend fun connect(host: String, port: Int = 18789, token: String) {
        try {
            _connectionState.value = ConnectionStatus.Connecting
            client.webSocket(method = HttpMethod.Get, host = host, port = port, path = "/") {
                _connectionState.value = ConnectionStatus.Connected
                
                // Simple handshake or auth if needed by TAU protocol
                send(Frame.Text("auth:$token"))

                while (isActive) {
                    val frame = incoming.receive()
                    if (frame is Frame.Text) {
                        println("Received from TAU: ${frame.readText()}")
                    }
                }
            }
        } catch (e: Exception) {
            _connectionState.value = ConnectionStatus.Error(e.message ?: "Unknown Error")
        } finally {
            _connectionState.value = ConnectionStatus.Disconnected
        }
    }
}

sealed class ConnectionStatus {
    object Disconnected : ConnectionStatus()
    object Connecting : ConnectionStatus()
    object Connected : ConnectionStatus()
    data class Error(val message: String) : ConnectionStatus()
}
