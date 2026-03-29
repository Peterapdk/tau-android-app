# Proposal: Android TAU Client Initialization

## Intent
Initialize a modern Android application that acts as a remote client for TAU, enabling interaction with the local agent framework over a Tailscale secure network.

## Scope
- Base Android project structure using Gradle (Kotlin DSL).
- Jetpack Compose for UI.
- Tailscale IP integration logic.
- WebSocket client for TAU gateway communication.

## Approach
1. Use `android-java` skill patterns for project structure.
2. Implement a background service for persistent TAU connection.
3. Secure communication using the provided OpenClaw gateway token.

## Acceptance Criteria
- Project compiles and runs on an emulator or physical device.
- Configurable host IP (defaulting to the verified Tailscale IP).
- Basic connection status indicator (Online/Offline).
