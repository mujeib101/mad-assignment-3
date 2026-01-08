# MAD Assignment 3 - Android Application

**Student Name:** [Your Name]  
**Reg No:** [Your Registration Number]  
**Course:** Mobile Application Development  
**Instructor:** Muhammad Azhar  
**Semester & Section:** 5 (A/B/C)

---

## 📱 Project Overview

This Android application demonstrates comprehensive mobile app development concepts including:
- Theme management with runtime switching
- User authentication with state persistence
- RESTful API integration
- Local SQLite database for offline access
- Multiple UI components and adapters
- Advanced menu systems
- WebView integration
- Activity lifecycle management

---

## 🎯 Features Implemented

### Section 1: Application Setup and Theme Management ✅
- ✅ **Multiple Themes**: Light, Dark, and Custom Blue theme
- ✅ **Runtime Theme Switching**: Via Options Menu
- ✅ **Theme Persistence**: Using SharedPreferences
- ✅ **Auto-apply Theme**: Theme applied on app restart

### Section 2: User State and Authentication ✅
- ✅ **Login Screen**: Simple authentication with validation
- ✅ **Authentication Flag**: `isLoggedIn` stored in SharedPreferences
- ✅ **Intent Navigation**: Auto-redirect based on login state
- ✅ **State Preservation**: During configuration changes (rotation)

### Section 3: Web Services / API Integration ✅
- ✅ **REST API**: JSONPlaceholder API (`https://jsonplaceholder.typicode.com/users`)
- ✅ **HTTP Client**: Retrofit with OkHttp
- ✅ **JSON Parsing**: GSON converter to model classes
- ✅ **Error Handling**: Network failure and empty response handling

### Section 4: Local Data Persistence (SQLite) ✅
- ✅ **Database Schema**: Comprehensive user table with all fields
- ✅ **Data Storage**: API responses saved to SQLite
- ✅ **Offline Access**: Data retrieved from database when offline
- ✅ **CRUD Operations**: 
  - Create (Insert users)
  - Read (Get all users, get user by ID)
  - Update (Edit user information)
  - Delete (Remove users)

### Section 5: Adapter Implementation ✅
- ✅ **Default Adapter**: ArrayAdapter with ListView
- ✅ **Custom Adapter**: RecyclerView.Adapter with ViewHolder
- ✅ **Data Binding**: SQLite data efficiently bound to UI
- ✅ **Click Handlers**: Item click events handled via adapters

### Section 6: Menu Implementation and Navigation ✅
- ✅ **Options Menu**: 
  - Theme switching (Light/Dark/Custom)
  - View mode toggle
  - Refresh data
  - Logout
- ✅ **Context Menu**: Long-press for Edit, Delete, View Details
- ✅ **Popup Menu**: Three-dot menu on each list item
- ✅ **Intent Navigation**: Between Login, Main, Detail, and WebView activities

### Section 7: WebView Integration ✅
- ✅ **WebView Component**: Display external web content
- ✅ **URL Loading**: User websites and location maps
- ✅ **JavaScript Enabled**: Full web functionality
- ✅ **Loading States**: Progress bar and error handling
- ✅ **In-app Browsing**: No external browser required

### Section 8: Input Controls and UI Interaction ✅
- ✅ **Input Controls**: 
  - EditText (username, password, edit dialogs)
  - Button (login, actions)
  - CheckBox (remember me)
  - RecyclerView & ListView
- ✅ **Input Validation**: 
  - Empty field checks
  - Minimum length validation
  - Email format (via API data)
- ✅ **UI Updates**: Real-time reflection of data changes

### Section 9: Activity Lifecycle and State Management ✅
- ✅ **Configuration Changes**: Rotation handled properly
- ✅ **State Preservation**: `onSaveInstanceState` and `onRestoreInstanceState`
- ✅ **Smart Data Loading**: No unnecessary API re-fetches
- ✅ **Memory Management**: Proper lifecycle handling, no leaks

---

## 🏗️ Architecture

### Project Structure
```
app/src/main/java/com/mujeeb/madassignment3/
├── adapters/
│   └── UserAdapter.kt               # Custom RecyclerView adapter
├── database/
│   └── DatabaseHelper.kt            # SQLite database handler
├── models/
│   └── User.kt                      # Data models
├── network/
│   ├── ApiService.kt                # Retrofit interface
│   └── RetrofitClient.kt            # Retrofit instance
├── ui/
│   ├── LoginActivity.kt             # Authentication screen
│   ├── MainActivity.kt              # Main user list screen
│   ├── UserDetailActivity.kt        # User detail view
│   └── WebViewActivity.kt           # Web content viewer
└── utils/
    ├── PreferencesManager.kt        # SharedPreferences wrapper
    └── ThemeManager.kt              # Theme management utility
```

### Database Schema
```sql
CREATE TABLE users (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    username TEXT NOT NULL,
    email TEXT NOT NULL,
    phone TEXT,
    website TEXT,
    address_street TEXT,
    address_suite TEXT,
    address_city TEXT,
    address_zipcode TEXT,
    address_lat TEXT,
    address_lng TEXT,
    company_name TEXT,
    company_catchphrase TEXT,
    company_bs TEXT
)
```

---

## 🔄 Navigation Flow

```
┌─────────────┐
│ LoginActivity│ ◄─── Check isLoggedIn flag
└──────┬──────┘
       │ (Login Success)
       ▼
┌──────────────┐
│ MainActivity  │ ◄─── Fetch API / Load from SQLite
└──┬───────┬───┘
   │       │
   │       └─────► Options Menu (Theme, Logout, Refresh)
   │       
   ├─────► Context Menu (Long press: Edit, Delete, Details)
   │
   ├─────► Popup Menu (3-dot: Edit, Delete, Website)
   │
   ├─────► Item Click
   │       ▼
   │   ┌──────────────────┐
   │   │ UserDetailActivity│
   │   └────┬─────────┬───┘
   │        │         │
   │        ▼         ▼
   │   View Website  View Map
   │        │         │
   │        └────┬────┘
   │             ▼
   │      ┌──────────────┐
   └─────►│ WebViewActivity│
          └──────────────┘
```

---

## 📊 API Details

**API Provider:** JSONPlaceholder  
**Base URL:** `https://jsonplaceholder.typicode.com/`  
**Endpoints Used:**
- `GET /users` - Fetch all users
- `GET /users/{id}` - Fetch single user

**Sample Response:**
```json
{
  "id": 1,
  "name": "Leanne Graham",
  "username": "Bret",
  "email": "Sincere@april.biz",
  "phone": "1-770-736-8031 x56442",
  "website": "hildegard.org",
  "address": {
    "street": "Kulas Light",
    "suite": "Apt. 556",
    "city": "Gwenborough",
    "zipcode": "92998-3874",
    "geo": { "lat": "-37.3159", "lng": "81.1496" }
  },
  "company": {
    "name": "Romaguera-Crona",
    "catchPhrase": "Multi-layered client-server neural-net",
    "bs": "harness real-time e-markets"
  }
}
```

---

## 🎨 UI Components Used

1. **Material Design Components**
   - MaterialCardView
   - FloatingActionButton
   - TextInputLayout
   - Material themes and colors

2. **Layout Managers**
   - LinearLayout
   - ConstraintLayout
   - ScrollView
   - CoordinatorLayout

3. **List Components**
   - RecyclerView with LinearLayoutManager
   - ListView with ArrayAdapter

4. **Input Controls**
   - EditText / TextInputEditText
   - Button / MaterialButton
   - CheckBox
   - ProgressBar

5. **Menus**
   - Options Menu (ActionBar)
   - Context Menu (Long press)
   - Popup Menu (3-dot)

---

## 🔧 Technologies & Libraries

- **Language:** Kotlin
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)
- **Build System:** Gradle

**Dependencies:**
```gradle
- AndroidX Core KTX
- AppCompat
- Material Components
- ConstraintLayout
- RecyclerView
- Retrofit 2.9.0
- GSON Converter
- OkHttp Logging Interceptor
- Kotlin Coroutines
- Lifecycle Components (ViewModel, LiveData)
```

---

## 🚀 How to Run

### Prerequisites
- Android Studio (Hedgehog or later)
- JDK 17
- Android SDK 34
- Internet connection (for API calls)

### Steps
1. **Clone the repository:**
   ```bash
   git clone https://github.com/mujeib101/mad-assignment-3.git
   cd mad-assignment-3
   ```

2. **Open in Android Studio:**
   - File → Open → Select project folder

3. **Sync Gradle:**
   - Wait for Gradle sync to complete
   - Download dependencies if needed

4. **Run the app:**
   - Connect device or start emulator
   - Click Run (▶️) or press Shift+F10
   - Select device and wait for installation

5. **Login:**
   - Username: Any text (min 3 characters)
   - Password: Any text (min 4 characters)

---

## 📸 Screenshots

### 1. Login Screen
- Material Design login form
- Input validation
- Theme persistence demonstration

### 2. Main Screen - RecyclerView
- User list with custom adapter
- Floating refresh button
- Status bar showing sync state

### 3. Main Screen - ListView (Toggle View)
- Simple ArrayAdapter implementation
- Alternative view mode

### 4. Options Menu
- Theme selection (Light/Dark/Custom)
- Refresh and Logout options
- View mode toggle

### 5. Context Menu
- Long-press activation
- Edit, Delete, View Details options

### 6. Popup Menu
- Three-dot menu on each item
- Quick actions

### 7. User Detail Screen
- Comprehensive user information
- Material cards
- Action buttons (Website, Map)

### 8. WebView Screen
- User website display
- Progress bar
- Back navigation

### 9. Theme Variations
- Light Theme
- Dark Theme
- Custom Blue Theme

### 10. Offline Mode
- Data loaded from SQLite
- Status message showing offline state

---

## ✨ Key Features Highlights

### 1. Offline-First Architecture
- Data always loaded from SQLite first
- Background API sync
- Works completely offline after first sync

### 2. Material Design 3
- Modern UI following Google's design guidelines
- Smooth animations
- Consistent color schemes

### 3. Smart State Management
- Configuration change handling
- No data loss on rotation
- Efficient memory usage

### 4. Error Handling
- Network error graceful handling
- Empty state management
- User-friendly error messages

### 5. Code Quality
- Clean architecture
- Separation of concerns
- Comprehensive comments
- Kotlin best practices

---

## 🔍 Testing Scenarios

### 1. Online Mode
- Launch app → Login → Data fetched from API → Stored in SQLite

### 2. Offline Mode
- Enable airplane mode → Launch app → Data loaded from SQLite

### 3. Theme Switching
- Change theme from Options Menu → App recreates with new theme → Theme persists after restart

### 4. CRUD Operations
- **Create:** Automatic on API sync
- **Read:** View list and details
- **Update:** Edit user name via dialog
- **Delete:** Remove user from list

### 5. Rotation Handling
- Rotate device → State preserved → No unnecessary API calls

### 6. Navigation
- Test all navigation paths
- Back button behavior
- Intent data passing

---

## 📝 Learning Outcomes

This project demonstrates proficiency in:
1. ✅ Android Activity lifecycle management
2. ✅ SharedPreferences for data persistence
3. ✅ SQLite database operations
4. ✅ RESTful API integration with Retrofit
5. ✅ RecyclerView and custom adapters
6. ✅ Material Design implementation
7. ✅ Menu systems (Options, Context, Popup)
8. ✅ WebView integration
9. ✅ Intent-based navigation
10. ✅ Kotlin coroutines for async operations
11. ✅ Configuration change handling
12. ✅ Input validation
13. ✅ Theme management
14. ✅ Clean code architecture

---

## 🐛 Known Issues / Future Enhancements

### Current Limitations
- No backend authentication (demo mode)
- No user registration
- No data validation against server

### Future Enhancements
- Add user creation functionality
- Implement search/filter
- Add sorting options
- Include pagination
- Add image loading for user avatars
- Implement pull-to-refresh gesture
- Add unit tests
- Include analytics

---

## 📚 References

1. **Android Documentation:** https://developer.android.com
2. **Material Design:** https://m3.material.io
3. **Retrofit:** https://square.github.io/retrofit/
4. **JSONPlaceholder API:** https://jsonplaceholder.typicode.com
5. **Kotlin Coroutines:** https://kotlinlang.org/docs/coroutines-overview.html

---

## 👨‍💻 Developer Information

**GitHub Repository:** https://github.com/mujeib101/mad-assignment-3  
**Email:** [Your Email]  
**Semester:** 5th  
**Program:** BSSE/BSCS

---

## 📄 License

This project is submitted as part of academic coursework for Mobile Application Development course.

---

## 🙏 Acknowledgments

- **Instructor:** Muhammad Azhar
- **Institution:** [Your University Name]
- **API Provider:** JSONPlaceholder by typicode
- **Design Inspiration:** Material Design 3

---

**Submission Date:** [Current Date]  
**Project Status:** ✅ Complete and Tested

---

## Appendix: Code Statistics

- **Total Activities:** 4
- **Total Kotlin Files:** 10
- **Total Layout Files:** 5
- **Total Menu Files:** 3
- **Lines of Code:** ~1500+
- **Database Tables:** 1
- **API Endpoints:** 2
