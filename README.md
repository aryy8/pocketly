# Pocketly 💳✨

A native Android personal finance application built with Kotlin and Jetpack Compose, featuring an authentic **Liquid Glass** aesthetic, custom typography (Inria Serif & Plus Jakarta Sans), smooth micro-interactions, and detailed spending analytics.

---

## 📱 Features

- **Liquid Glass Aesthetic**: Translucent glass surfaces with specular top highlights, subtle border strokes, and realistic Gaussian drop shadows.
- **Dynamic Balance & Spend Card**: Real-time spending breakdown and available balance tracking with smooth trend indicators.
- **Connected Accounts**: Multi-bank account carousel with authentic logos (HDFC Bank, SBI) and dropdown pickers.
- **Spending Analytics**: Custom Donut Chart with categorized breakdowns (Food, Mobile Bills, Shopping, Transport).
- **Transaction History**: Recents card on the home feed and a dedicated, filterable **Transactions Page** grouped across months.
- **Custom Bottom Navigation**: Frosted floating navigation bar with custom vector iconography and quick tab switching.

---

## 🛠 Tech Stack

- **Language**: Kotlin
- **UI Toolkit**: Jetpack Compose
- **Design System**: Liquid Glass + Material 3
- **Typography**: Inria Serif & Plus Jakarta Sans
- **Build System**: Gradle 9.1 with Kotlin DSL

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug (or newer)
- Android SDK 35 (compileSdk 35, minSdk 26)
- JDK 17+

### Build & Run
```bash
# Clone the repository
git clone https://github.com/aryy8/pocketly.git
cd pocketly

# Build Debug APK
./gradlew assembleDebug

# Install on connected device or running emulator
./gradlew installDebug
```
