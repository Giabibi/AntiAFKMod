# Changelog

All notable changes to this project will be documented in this file.

## [1.1.0] - 2025-07-19

### Added

-   Support for a **randomized interval** between actions using `minIntervalMinutes` and `maxIntervalMinutes`
-   Separated **configuration categories** in ModMenu: _General_, _Movement_, _Look_
-   **Configurable head movement steps** and delay between steps (`headMovementSteps`, `headMovementDelayMs`)
-   New fields to control **yaw and pitch rotation strength** (`maxYawStrength`, `maxPitchStrength`)
-   Configurable **movement intensity** (`maxMoveIntensity`)
-   Tooltip support for all config entries

### Changed

-   Toggle message is now **color-coded** in green/red for enabled/disabled
-   Increased max limits for yaw/pitch strength for more realistic/random head movement

### Fixed

-   Fixed config values not being properly saved and reloaded without restarting
-   Timer restarts with new values when toggling the mod

---

## [1.0.0] - 2025-07-15

### Added

-   Initial version of AntiAFKMod
-   Basic anti-AFK behavior: jump, move, and look every X minutes
-   Config file stored in `config/antiafkmod.json`
-   ModMenu integration with toggles and settings
-   Keybind toggle (`K` by default) to enable/disable the mod
