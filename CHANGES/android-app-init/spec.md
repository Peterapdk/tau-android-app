# Specification: Android TAU Client

## Functional Requirements
- **FR-1**: User can input/update the TAU host IP (Tailscale).
- **FR-2**: App must persist the OpenClaw token securely.
- **FR-3**: Real-time status updates from the TAU gateway.
- **FR-4**: Basic chat interface for sending/receiving messages to/from TAU.

## Non-Functional Requirements
- **NFR-1**: UI must follow Material Design 3.
- **NFR-2**: Network operations must be asynchronous and non-blocking.
- **NFR-3**: Handle Tailscale disconnection gracefully.

## Scenarios

### Scenario 1: Initial Connection
**Given** the user has installed the app and TAU is running on revi,
**When** the user enters the Tailscale IP and token,
**Then** the app establishes a WebSocket connection and shows "Connected".

### Scenario 2: Network Loss
**Given** an active connection,
**When** the Tailscale VPN is turned off or network is lost,
**Then** the app shows "Reconnecting..." and attempts to back off.
