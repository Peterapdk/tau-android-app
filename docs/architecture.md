# Architecture: Android to OMP/TAU Connectivity

## Overview
The Android application connects to the OMP/TAU instance running on **revi** via Tailscale. This setup uses the OMP-integrated TAU gateway.

## Connection Details
- **Target Host (Tailscale IP)**: `100.91.199.107`
- **Port**: `3001` (OMP Gateway Port)
- **Base URL**: `http://100.91.199.107:3001/`
- **Protocol**: HTTP / WebSocket (wss:// or ws://)

## Protocol Stack
1. **Network**: Tailscale VPN
2. **Transport**: TCP
3. **Session**: HTTP/WebSocket
4. **Application**: OMP RPC Protocol

## Android Implementation
- **Ktor Client**: Configured for port 3001.
- **Protocol**: Support for OMP-specific JSON-RPC or message frames.
