# Architecture: Android to TAU Connectivity

## Overview
The Android application will connect to the TAU instance running on **revi** via Tailscale. TAU (running via OpenClaw) typically exposes a WebSocket gateway for RPC and event streaming.

## Connection Details
- **Target Host (Tailscale IP)**: `100.91.199.107`
- **Port**: `18789` (Standard OpenClaw/TAU port)
- **Protocol**: WebSocket (wss:// or ws://)
- **Auth**: Token-based (as configured in `openclaw.json`)

## Protocol Stack
1. **Physical/Network**: Tailscale VPN (Direct peer-to-peer)
2. **Transport**: TCP
3. **Session**: WebSocket
4. **Application**: OpenClaw RPC / Event Protocol

## Gateway Configuration Requirements
The TAU gateway on **revi** must be configured to bind to `0.0.0.0` or the Tailscale interface specifically, instead of `loopback (127.0.0.1)`, to allow external connections from the Android device.

## Android Implementation Strategy
- **Language**: Kotlin
- **Framework**: Jetpack Compose
- **Networking**: OkHttp + Scarlet (for WebSockets) or Ktor-Client
- **Connectivity**: Check for Tailscale active status before attempting connection.
