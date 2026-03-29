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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tau.client.data.SettingsRepository
import com.tau.client.network.TauGatewayClient
import com.tau.client.ui.MainScreen
import com.tau.client.ui.MainViewModel
import com.tau.client.ui.theme.TAUAndroidAppTheme
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Manual DI for bootstrap phase
        val repository = SettingsRepository(applicationContext)
        val httpClient = HttpClient { install(WebSockets) }
        val gatewayClient = TauGatewayClient(httpClient)
        val viewModel = MainViewModel(repository, gatewayClient)

        setContent {
            TAUAndroidAppTheme {
                val status by viewModel.connectionStatus.collectAsStateWithLifecycle()
                val host by viewModel.host.collectAsStateWithLifecycle()
                val token by viewModel.token.collectAsStateWithLifecycle()
                val messages by viewModel.messages.collectAsStateWithLifecycle()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        connectionStatus = status,
                        host = host,
                        token = token,
                        messages = messages,
                        onConnect = { h, t -> viewModel.connect(h, t) },
                        onSendMessage = { m -> viewModel.sendMessage(m) }
                    )
                }
            }
        }
    }
}
