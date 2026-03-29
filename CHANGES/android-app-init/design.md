# Design: Android TAU Client

## Tech Stack
- **UI**: Jetpack Compose
- **Concurrency**: Kotlin Coroutines & Flow
- **Network**: Ktor Client (WebSocket + Engine OkHttp)
- **Dependency Injection**: Koin (Lightweight for simple app)
- **Storage**: DataStore (for IP and Token)

## Component Diagram
- `MainActivity`: Host for Compose UI.
- `ConnectionViewModel`: Manages connection state and user inputs.
- `TauGatewayClient`: Handles WebSocket lifecycle and protocol parsing.
- `SettingsRepository`: Persists host and auth data.

## Connection Flow
1. `App Launch` -> Load settings from `DataStore`.
2. `TauGatewayClient` -> Attempt WebSocket connection to `ws://[IP]:18789`.
3. `Handshake` -> Send OpenClaw token.
4. `Active` -> Listen for events, allow user messages.

## Data Schema (Local Storage)
- `tau_host`: String (e.g., "100.91.199.107")
- `tau_token`: String (e.g., "fjrtsale-secure-2026")
