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
    port: Int,
    token: String,
    messages: List<String>,
    onConnect: (String, Int, String) -> Unit,
    onSendMessage: (String) -> Unit
) {
    var hostInput by remember { mutableStateOf(host) }
    var portInput by remember { mutableStateOf(port.toString()) }
    var tokenInput by remember { mutableStateOf(token) }
    var messageInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("TAU Android OMP Client") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = hostInput,
                            onValueChange = { hostInput = it },
                            label = { Text("Host IP") },
                            modifier = Modifier.weight(0.7f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = portInput,
                            onValueChange = { portInput = it },
                            label = { Text("Port") },
                            modifier = Modifier.weight(0.3f)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tokenInput,
                        onValueChange = { tokenInput = it },
                        label = { Text("Auth Token") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { onConnect(hostInput, portInput.toIntOrNull() ?: 3001, tokenInput) },
                        modifier = Modifier.align(Alignment.End),
                        enabled = connectionStatus is ConnectionStatus.Disconnected || connectionStatus is ConnectionStatus.Error
                    ) {
                        Text(if (connectionStatus is ConnectionStatus.Connected) "Connected" else "Connect")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            StatusRow(status = connectionStatus, host = hostInput, port = portInput)
            Spacer(modifier = Modifier.height(16.dp))

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

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = messageInput,
                    onValueChange = { messageInput = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Message TAU...") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { 
                    onSendMessage(messageInput)
                    messageInput = ""
                }) {
                    Text("Send")
                }
            }
        }
    }
}

@Composable
fun StatusRow(status: ConnectionStatus, host: String, port: String) {
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
                is ConnectionStatus.Connected -> "Connected to OMP gateway"
                is ConnectionStatus.Connecting -> "Connecting to $host:$port..."
                is ConnectionStatus.Error -> "Error: ${status.message}"
                else -> "Disconnected"
            },
            style = MaterialTheme.typography.labelLarge
        )
    }
}
