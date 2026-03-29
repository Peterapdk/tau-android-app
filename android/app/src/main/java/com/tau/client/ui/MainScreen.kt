package com.tau.client.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tau.client.network.ConnectionStatus

@OptIn(Material3Api::class)
@Composable
fun MainScreen(
    connectionStatus: ConnectionStatus,
    host: String,
    token: String,
    messages: List<String>,
    onConnect: (String, String) -> Unit,
    onSendMessage: (String) -> Unit
) {
    var hostInput by remember { mutableStateOf(host) }
    var tokenInput by remember { mutableStateOf(token) }
    var messageInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("TAU Android Client") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Configuration Card
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = hostInput,
                        onValueChange = { hostInput = it },
                        label = { Text("Tailscale IP") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tokenInput,
                        onValueChange = { tokenInput = it },
                        label = { Text("Auth Token") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { onConnect(hostInput, tokenInput) },
                        modifier = Modifier.align(Alignment.End),
                        enabled = connectionStatus is ConnectionStatus.Disconnected || connectionStatus is ConnectionStatus.Error
                    ) {
                        Text(if (connectionStatus is ConnectionStatus.Connected) "Connected" else "Connect")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Status Indicator
            StatusRow(status = connectionStatus)

            Spacer(modifier = Modifier.height(16.dp))

            // Message Log
            Text("Activity Log", style = MaterialTheme.typography.titleMedium)
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                items(messages) { msg ->
                    Text(msg, style = MaterialTheme.typography.bodySmall)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                }
            }

            // Input Row
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = messageInput,
                    onValueChange = { messageInput = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Message TAU...") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { 
                    onSendMessage(messageInput)
                    messageInput = ""
                }) {
                    Text("Send") // Replace with icon in real app
                }
            }
        }
    }
}

@Composable
fun StatusRow(status: ConnectionStatus) {
    val color = when (status) {
        is ConnectionStatus.Connected -> MaterialTheme.colorScheme.primary
        is ConnectionStatus.Connecting -> MaterialTheme.colorScheme.secondary
        is ConnectionStatus.Error -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.outline
    }
    
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            modifier = Modifier.size(12.dp),
            shape = MaterialTheme.shapes.small,
            color = color
        ) {}
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = when (status) {
                is ConnectionStatus.Connected -> "Connected to revi"
                is ConnectionStatus.Connecting -> "Connecting to 100.91.199.107..."
                is ConnectionStatus.Error -> "Error: ${status.message}"
                else -> "Disconnected"
            },
            style = MaterialTheme.typography.labelLarge
        )
    }
}
