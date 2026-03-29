# Tasks: Android TAU Client Initialization

## Setup
- [ ] Initialize Android Project with Gradle (Kotlin DSL) `task:android-gradle-init`
- [ ] Add necessary dependencies (Ktor, DataStore, Koin) `task:android-deps`

## Core Implementation
- [ ] Implement `SettingsRepository` using DataStore `task:android-settings`
- [ ] Build `TauGatewayClient` with WebSocket support `task:android-network`
- [ ] Create `ConnectionViewModel` to bridge UI and Network `task:android-vm`

## UI Implementation
- [ ] Design Connection Screen (IP/Token entry) `task:android-ui-connection`
- [ ] Implement Status Indicator component `task:android-ui-status`
- [ ] Simple Message log view `task:android-ui-chat`

## Verification
- [ ] Verify connection to `revi` local mock (if possible)
- [ ] Lint check and final cleanup
