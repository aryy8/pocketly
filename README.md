# Pocketly

Pocketly is a native Android personal finance application built with Kotlin and Jetpack Compose. It features a custom Liquid Glass design system, fine-tuned typography (Inria Serif and Plus Jakarta Sans), smooth trackpad and touch physics, and modular transaction management.

The project is structured to encourage open-source contributions, featuring clean separation of concerns across reusable components, stateful screens, and theme definitions.

---

## Architectural Highlights

- **Declarative UI**: 100% Jetpack Compose with Material 3 integration.
- **Liquid Glass Design System**: Reusable composables (`GlassCard`, `GlassPill`, custom brushes, specular highlight borders, and elevation-driven drop shadows).
- **Smooth Momentum Scrolling**: Custom fling behavior and trackpad scroll event interception optimized for both high-refresh-rate mobile touchscreens and desktop emulators.
- **State Hoisting**: Predictable unidirectional data flow across all cards, dialogs, and navigation destinations.

---

## Technology Stack

- **Language**: Kotlin 1.9+
- **UI Framework**: Jetpack Compose (Compose BOM)
- **Design Foundation**: Material 3 + Custom Liquid Glass Theme
- **Typography**: Inria Serif (display headings), Plus Jakarta Sans (body, numerals, labels)
- **Minimum SDK**: Android API 26 (Android 8.0 Oreo)
- **Target SDK**: Android API 35 (Android 15)
- **Build System**: Gradle with Kotlin DSL (`build.gradle.kts`)
- **JDK**: Java 17 (recommended: Zulu OpenJDK 17)

---

## Project Structure

```text
app/src/main/java/com/pocketly/app/
|-- MainActivity.kt                 # Application entry point and splash coordination
|-- ui/
    |-- components/                 # Reusable UI widgets and dialogs
    |   |-- BalanceCard.kt          # Available balance and spend breakdown card
    |   |-- BottomNavBar.kt         # Custom frosted floating navigation bar
    |   |-- ConnectedAccountModals.kt # Add Account dialog and Account Detail dialog
    |   |-- ConnectedAccountsCard.kt  # Bank account pill carousel and quick actions
    |   |-- DonutChart.kt           # Canvas-drawn segmented spending breakdown ring
    |   |-- GreetingSection.kt      # User greeting and active date header
    |   |-- MonthlySpendingCard.kt  # Spend summary card with interactive month picker
    |   |-- TopBar.kt               # Liquid glass top bar with Inria Serif branding
    |   |-- TransactionDetailDialog.kt # Modal receipt dialog with invoice actions
    |   |-- TransactionHistoryCard.kt  # Recent transaction feed card
    |-- screens/                    # Full-screen Composable destinations
    |   |-- HomeScreen.kt           # Dashboard feed orchestrating summary cards
    |   |-- TransactionsScreen.kt   # Filterable monthly transaction history
    |   |-- SearchScreen.kt         # Real-time search with suggestion pills
    |   |-- AnalyticsScreen.kt      # Monthly comparison bar charts and budget tracking
    |   |-- ProfileScreen.kt        # User profile, UPI VPA copy, and security toggles
    |   |-- SplashScreen.kt         # Launch screen with branding
    |-- theme/                      # Design system tokens and styling
        |-- Color.kt                # Liquid glass palettes, brand tones, chart accents
        |-- Glass.kt                # GlassCard and GlassPill composables
        |-- ScrollConfig.kt         # Trackpad scroll smoothing and fling momentum
        |-- Theme.kt                # MaterialTheme wrapper
        |-- Type.kt                 # Inria Serif and Plus Jakarta Sans font definitions
```

---

## Local Development Setup

### Prerequisites

1. Install **Android Studio Ladybug (2024.2+)** or later.
2. Ensure **JDK 17** is configured in Android Studio (`Settings > Build, Execution, Deployment > Build Tools > Gradle > Gradle JDK`).
3. Android SDK Platforms: Install **API 35** and **API 34** via SDK Manager.
4. An active Android Virtual Device (AVD) or a physical device with USB Debugging enabled.

### Clone and Build

```bash
# Clone the repository
git clone https://github.com/aryy8/pocketly.git
cd pocketly

# Build the debug APK
./gradlew assembleDebug

# Install on a running emulator or connected device
./gradlew installDebug
```

### Running from Android Studio

1. Open Android Studio and select **Open**.
2. Navigate to the cloned `pocketly` directory and click **OK**.
3. Allow Gradle to sync dependencies.
4. Select `app` in the run configuration dropdown and choose your target emulator/device.
5. Click **Run** (Shift + F10).

---

## Contributing to Pocketly

We welcome contributions of all skill levels, from fixing typos and adding tests to implementing new features and architecture improvements.

### Development Workflow

1. **Fork the Repository**: Create your own fork of `pocketly`.
2. **Clone your Fork**:
   ```bash
   git clone https://github.com/<your-username>/pocketly.git
   cd pocketly
   ```
3. **Create a Feature Branch**:
   ```bash
   git checkout -b feature/your-feature-name
   # or
   git checkout -b fix/your-bug-fix
   ```
4. **Follow Coding Standards**:
   - Write clean, idiomatic Kotlin code.
   - Keep Composables stateless and hoist state wherever practical.
   - Use design tokens from `com.pocketly.app.ui.theme` rather than hardcoded color or typography values.
   - Do not commit image files, screenshots, or local build caches (already configured in `.gitignore`).
5. **Verify Compilation**:
   ```bash
   ./gradlew compileDebugKotlin
   ./gradlew assembleDebug
   ```
6. **Commit with Conventional Commit Messages**:
   - `feat: add biometric lock support`
   - `fix: correct transaction date sorting`
   - `refactor: extract search filter logic into ViewModel`
   - `docs: update setup instructions`
7. **Open a Pull Request**: Submit your PR targeting the `main` branch with a clear description of the changes made and screenshots/recordings if UI changes are involved.

---

## License

This project is licensed under the Apache License 2.0. See the `LICENSE` file for details.
