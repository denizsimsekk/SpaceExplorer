# 🚀 Space Explorer App - Compose Multiplatform 🚀

## 👩🏻‍💻 Architecture
The project uses **MVVM** and **Clean Architecture** to keep the code organized and easy to maintain.

- **Data Layer**:  
  Responsible for fetching data from APIs, saving data to the local database, and retrieving data from it. A `BaseRepository` is implemented to reduce boilerplate code and standardize common operations.

- **Domain Layer**:  
  Contains **use cases**, **view entities**, and **repository interfaces** to abstract the business logic from the data layer. This keeps the core application logic separate and easy to test.

- **Presentation Layer**:  
  Contains **views** and **viewmodels**. A `BaseViewModel` is provided to simplify data fetching and reduce repetitive code. A `BaseScreen` is implemented to reuse common background layouts and UI logic across screens.

## 📱 Expect/Actual Mechanism
The project leverages **Kotlin Multiplatform's `expect/actual` mechanism** for platform-specific implementations.  
- Functions for **WebView** or **SQLDelight** and date format function use this pattern, so the shared code works on all platforms while keeping platform-specific functionality.

## 🛠️ Libraries & Tools

The project uses the following libraries and tools:

- **Ktor** – for making network requests  
- **Koin** – for dependency injection  
- **Coil** – for image loading  
- **Navigation 3** – for in-app navigation  
- **SQLDelight** – for database management

## 🧪 Unit Testing

The project includes **unit tests** for use cases, repositories, and viewmodels using **Kotlin Test**.  
- Shared business logic is tested in a multiplatform-friendly way.
- Tests use fake data and mocks to check the logic without relying on APIs or databases.
