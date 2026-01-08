# Database Schema Diagram

## Users Table Structure

```
┌─────────────────────────────────────────────────────────────────┐
│                         USERS TABLE                              │
├─────────────────────────┬────────────────────────────────────────┤
│ Column Name             │ Data Type & Constraints                │
├─────────────────────────┼────────────────────────────────────────┤
│ id                      │ INTEGER PRIMARY KEY                    │
│ name                    │ TEXT NOT NULL                          │
│ username                │ TEXT NOT NULL                          │
│ email                   │ TEXT NOT NULL                          │
│ phone                   │ TEXT                                   │
│ website                 │ TEXT                                   │
│ address_street          │ TEXT                                   │
│ address_suite           │ TEXT                                   │
│ address_city            │ TEXT                                   │
│ address_zipcode         │ TEXT                                   │
│ address_lat             │ TEXT                                   │
│ address_lng             │ TEXT                                   │
│ company_name            │ TEXT                                   │
│ company_catchphrase     │ TEXT                                   │
│ company_bs              │ TEXT                                   │
└─────────────────────────┴────────────────────────────────────────┘
```

## CRUD Operations

### CREATE (Insert)
```kotlin
fun insertUser(user: User): Long
fun insertUsers(users: List<User>)
```

### READ (Select)
```kotlin
fun getAllUsers(): List<User>
fun getUserById(userId: Int): User?
fun getUserCount(): Int
```

### UPDATE
```kotlin
fun updateUser(user: User): Int
```

### DELETE
```kotlin
fun deleteUser(userId: Int): Int
fun deleteAllUsers(): Int
```

---

# Navigation Flow Diagram

```
                    ┌─────────────────────────────────┐
                    │      App Launch                 │
                    └───────────────┬─────────────────┘
                                    │
                                    ▼
                    ┌─────────────────────────────────┐
                    │   Check SharedPreferences       │
                    │   isLoggedIn = ?                │
                    └───────────┬───────────┬─────────┘
                          false │           │ true
                                │           │
                ┌───────────────▼───┐       │
                │                   │       │
            ┌───┴─────────────────┐ │       │
            │   LoginActivity     │ │       │
            │                     │ │       │
            │  ┌──────────────┐  │ │       │
            │  │ Username     │  │ │       │
            │  │ Password     │  │ │       │
            │  │ Remember Me  │  │ │       │
            │  │ [Login Btn]  │  │ │       │
            │  └──────────────┘  │ │       │
            └─────────────────────┘ │       │
                        │           │       │
                        └───────────┼───────┘
                                    │
                                    ▼
            ┌───────────────────────────────────────────────────┐
            │              MainActivity                         │
            │  ┌─────────────────────────────────────────────┐ │
            │  │  [Status Bar: Online/Offline]               │ │
            │  │  [Progress Bar]                             │ │
            │  ├─────────────────────────────────────────────┤ │
            │  │  ┌───────────────────────────────────────┐  │ │
            │  │  │  RecyclerView / ListView              │  │ │
            │  │  │  ┌─────────────────────────────────┐  │  │ │
            │  │  │  │  User Item                      │  │  │ │
            │  │  │  │  - Name                         │  │  │ │
            │  │  │  │  - Email                        │  │  │ │
            │  │  │  │  - Phone                        │  │  │ │
            │  │  │  │  - Company                [⋮] ◄─┼──┼──┼─┼─ Popup Menu
            │  │  │  └─────────────────────────────────┘  │  │ │
            │  │  │  ┌─────────────────────────────────┐  │  │ │
            │  │  │  │  User Item                      │  │  │ │
            │  │  │  └─────────────────────────────────┘  │  │ │
            │  │  │            ...                        │  │ │
            │  │  └───────────────────────────────────────┘  │ │
            │  │                                             │ │
            │  │  [FAB: Refresh] ◄───────────────────────────┼─┼─ Refresh Data
            │  └─────────────────────────────────────────────┘ │
            │                                                   │
            │  ┌─────────────────────────────────────────────┐ │
            │  │ Options Menu (⋮)                            │ │
            │  │  - Refresh                                  │ │
            │  │  - Toggle View Mode                         │ │
            │  │  - Theme > Light/Dark/Custom                │ │
            │  │  - Logout                                   │ │
            │  └─────────────────────────────────────────────┘ │
            │                                                   │
            │  ┌─────────────────────────────────────────────┐ │
            │  │ Context Menu (Long Press)                   │ │
            │  │  - View Details                             │ │
            │  │  - Edit                                     │ │
            │  │  - Delete                                   │ │
            │  └─────────────────────────────────────────────┘ │
            │                                                   │
            │  ┌─────────────────────────────────────────────┐ │
            │  │ Popup Menu (⋮)                              │ │
            │  │  - Edit                                     │ │
            │  │  - Delete                                   │ │
            │  │  - View Website                             │ │
            │  └─────────────────────────────────────────────┘ │
            └──────┬──────────────────┬─────────────┬──────────┘
                   │                  │             │
        ┌──────────▼──────┐  ┌────────▼───────┐   │
        │ View Details    │  │  Edit Dialog   │   │
        │                 │  │  ┌──────────┐  │   │
        │                 │  │  │ New Name │  │   │
        │                 │  │  │  [Save]  │  │   │
        └────────┬────────┘  └────────────────┘   │
                 │                                 │
                 ▼                                 │
    ┌────────────────────────────────┐            │
    │   UserDetailActivity           │            │
    │  ┌──────────────────────────┐  │            │
    │  │ [User Name Card]         │  │            │
    │  │ @username                │  │            │
    │  ├──────────────────────────┤  │            │
    │  │ Contact Information      │  │            │
    │  │ - Email                  │  │            │
    │  │ - Phone                  │  │            │
    │  │ - Website                │  │            │
    │  ├──────────────────────────┤  │            │
    │  │ Address                  │  │            │
    │  ├──────────────────────────┤  │            │
    │  │ Company                  │  │            │
    │  ├──────────────────────────┤  │            │
    │  │ [View Website]           │  │            │
    │  │ [View on Map]            │  │            │
    │  └──────────────────────────┘  │            │
    └──────────┬──────────┬──────────┘            │
               │          │                        │
               │          └────────────────────────┘
               │                                   │
               ▼                                   ▼
    ┌──────────────────────────────────────────────────┐
    │          WebViewActivity                         │
    │  ┌────────────────────────────────────────────┐  │
    │  │ [Progress Bar]                             │  │
    │  ├────────────────────────────────────────────┤  │
    │  │                                            │  │
    │  │        [Web Content Display]              │  │
    │  │                                            │  │
    │  │  - User Website                            │  │
    │  │  - OpenStreetMap Location                  │  │
    │  │  - JavaScript Enabled                      │  │
    │  │  - Full Browser Features                   │  │
    │  │                                            │  │
    │  └────────────────────────────────────────────┘  │
    └──────────────────────────────────────────────────┘
```

---

# Data Flow Diagram

```
┌───────────────────────────────────────────────────────────────────┐
│                        Application Start                          │
└──────────────────────────┬────────────────────────────────────────┘
                           │
                           ▼
              ┌────────────────────────┐
              │  SharedPreferences     │
              │  Check: isLoggedIn     │
              └────────┬───────────────┘
                       │
                       ▼
              ┌─────────────────────┐
              │   MainActivity      │
              └──────────┬──────────┘
                         │
         ┌───────────────┼───────────────┐
         │               │               │
         ▼               ▼               ▼
    ┌────────┐    ┌──────────┐    ┌──────────┐
    │ SQLite │    │ Retrofit │    │   UI     │
    │   DB   │    │   API    │    │ Updates  │
    └────┬───┘    └─────┬────┘    └────┬─────┘
         │              │              │
         │         ┌────▼──────┐       │
         │         │  Network  │       │
         │         │  Request  │       │
         │         └────┬──────┘       │
         │              │              │
         │         ┌────▼──────────┐   │
         │         │ JSON Response │   │
         │         │  User List    │   │
         │         └────┬──────────┘   │
         │              │              │
         ▼              ▼              ▼
    ┌────────────────────────────────────┐
    │     Data Processing Layer          │
    │  - Parse JSON to User objects      │
    │  - Save to SQLite                  │
    │  - Update UI with data             │
    └────────────────────────────────────┘
                     │
                     ▼
         ┌───────────────────────┐
         │   User Interactions   │
         │  - Click              │
         │  - Long Press         │
         │  - Menu Actions       │
         └───────────┬───────────┘
                     │
         ┌───────────┼───────────┐
         │           │           │
         ▼           ▼           ▼
    ┌────────┐  ┌───────┐  ┌─────────┐
    │ View   │  │ Edit  │  │ Delete  │
    │ Detail │  │ User  │  │  User   │
    └────────┘  └───┬───┘  └────┬────┘
                    │           │
                    ▼           ▼
              ┌──────────────────────┐
              │  Update SQLite DB    │
              └──────────┬───────────┘
                         │
                         ▼
              ┌──────────────────────┐
              │  Refresh UI          │
              └──────────────────────┘
```

---

# Theme Switching Flow

```
┌─────────────────────────────────────────────────────────────┐
│               User Selects Theme from Menu                  │
└───────────────────────────┬─────────────────────────────────┘
                            │
                ┌───────────┼───────────┐
                │           │           │
                ▼           ▼           ▼
         ┌──────────┐ ┌─────────┐ ┌──────────┐
         │  Light   │ │  Dark   │ │  Custom  │
         │  Theme   │ │  Theme  │ │  Theme   │
         └────┬─────┘ └────┬────┘ └────┬─────┘
              │            │           │
              └────────────┼───────────┘
                           │
                           ▼
              ┌────────────────────────┐
              │   ThemeManager         │
              │   .changeTheme()       │
              └────────────┬───────────┘
                           │
                           ▼
              ┌────────────────────────┐
              │  PreferencesManager    │
              │  Save theme to         │
              │  SharedPreferences     │
              └────────────┬───────────┘
                           │
                           ▼
              ┌────────────────────────┐
              │   activity.recreate()  │
              │   Apply new theme      │
              └────────────┬───────────┘
                           │
                           ▼
              ┌────────────────────────┐
              │   onCreate()           │
              │   Load saved theme     │
              │   Apply to activity    │
              └────────────────────────┘
```

---

# Offline Mode Architecture

```
                      ┌────────────────┐
                      │   App Start    │
                      └────────┬───────┘
                               │
                               ▼
                  ┌────────────────────────┐
                  │ Load from SQLite First │
                  │ (Immediate Data)       │
                  └────────┬───────────────┘
                           │
                           ▼
                  ┌────────────────────────┐
                  │  Display Data to User  │
                  │  Status: "From Cache"  │
                  └────────┬───────────────┘
                           │
                           ▼
              ┌────────────────────────────┐
              │  Check Network Connection  │
              └────────┬───────────┬───────┘
                       │           │
               Online  │           │  Offline
                       │           │
                       ▼           ▼
          ┌─────────────────┐  ┌──────────────────┐
          │  Fetch from API │  │ Continue with    │
          │  in Background  │  │ Local Data Only  │
          └────────┬────────┘  └──────────────────┘
                   │
                   ▼
          ┌──────────────────┐
          │  Parse Response  │
          └────────┬─────────┘
                   │
                   ▼
          ┌──────────────────┐
          │  Update SQLite   │
          │  (New/Changed)   │
          └────────┬─────────┘
                   │
                   ▼
          ┌──────────────────┐
          │   Refresh UI     │
          │ Status: "Synced" │
          └──────────────────┘
```

---

# API Integration Flow

```
┌─────────────────────────────────────────────────────────────┐
│                   Retrofit Setup                            │
│  - Base URL: https://jsonplaceholder.typicode.com          │
│  - Converter: GSON                                          │
│  - Logging Interceptor                                      │
└───────────────────────────┬─────────────────────────────────┘
                            │
                            ▼
              ┌─────────────────────────┐
              │     ApiService          │
              │  @GET("users")          │
              │  suspend fun getUsers() │
              └────────────┬────────────┘
                           │
                           ▼
              ┌─────────────────────────┐
              │   Coroutine Launch      │
              │   (Background Thread)   │
              └────────────┬────────────┘
                           │
                           ▼
              ┌─────────────────────────┐
              │   HTTP GET Request      │
              └────────────┬────────────┘
                           │
         ┌─────────────────┼─────────────────┐
         │ Success         │         Failure │
         ▼                 ▼                 ▼
┌─────────────────┐  ┌──────────┐  ┌────────────────┐
│ JSON Response   │  │  Empty   │  │ Network Error  │
│ Status: 200     │  │ Response │  │ Exception      │
└────────┬────────┘  └────┬─────┘  └────────┬───────┘
         │                │                  │
         ▼                ▼                  ▼
┌──────────────────────────────────────────────────┐
│           Response Handling                      │
│  - Parse JSON to List<User>                      │
│  - Validate data                                 │
│  - Handle errors gracefully                      │
└────────────────────┬─────────────────────────────┘
                     │
                     ▼
        ┌────────────────────────┐
        │  Save to SQLite        │
        │  (Offline Storage)     │
        └────────────┬───────────┘
                     │
                     ▼
        ┌────────────────────────┐
        │  Update UI on          │
        │  Main Thread           │
        └────────────────────────┘
```

This comprehensive diagram file should help visualize all aspects of the application for your submission!
