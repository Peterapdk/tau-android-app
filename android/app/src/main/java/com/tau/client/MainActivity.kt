package com.tau.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.tau.client.data.SettingsRepository
import com.tau.client.network.ConnectionStatus
import com.tau.client.network.TauGatewayClient
import com.tau.client.ui.MainScreen
import com.tau.client.ui.MainViewModel
import com.tau.client.ui.theme.TAUAndroidAppTheme
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Manual DI
        val repository = SettingsRepository(applicationContext)
        val httpClient = HttpClient { install(WebSockets) }
        val gatewayClient = TauGatewayClient(httpClient)
        val viewModel = MainViewModel(repository, gatewayClient)

        setContent {
            TAUAndroidAppTheme {
                val status by viewModel.connectionStatus.collectAsState(ConnectionStatus.Disconnected)
                val host by viewModel.host.collectAsState("")
                val port by viewModel.port.collectAsState(3001)
                val token by viewModel.token.collectAsState("")
                val messages by viewModel.messages.collectAsState(emptyList())

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        connectionStatus = status,
                        host = host,
                        port = port,
                        token = token,
                        messages = messages,
                        onConnect = { h, p, t -> viewModel.connect(h, p, t) },
                        onSendMessage = { m -> viewModel.sendMessage(m) }
                    )
                }
            }
        }
    }
}
