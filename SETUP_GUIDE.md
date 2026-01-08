# Quick Setup Guide

## Initial Setup Steps

### 1. Clone and Open Project
```bash
git clone https://github.com/mujeib101/mad-assignment-3.git
cd mad-assignment-3
```
Open in Android Studio: File → Open → Select project folder

### 2. Wait for Gradle Sync
- Android Studio will automatically sync Gradle
- Download dependencies (may take a few minutes)
- Ensure internet connection is active

### 3. Configure SDK (if needed)
- File → Project Structure → SDK Location
- Ensure Android SDK is installed
- Recommended SDK: API 34

### 4. Run the Application

#### On Emulator:
1. Tools → Device Manager
2. Create Virtual Device (if not exists)
3. Recommended: Pixel 5, API 34, x86_64
4. Click Run (▶️)

#### On Physical Device:
1. Enable Developer Options on your phone
2. Enable USB Debugging
3. Connect via USB
4. Allow USB debugging when prompted
5. Click Run (▶️)

## Testing the App

### Login Screen
- **Username:** Any text (minimum 3 characters)
- **Password:** Any text (minimum 4 characters)
- Example: Username: `admin`, Password: `1234`

### Main Features to Test

1. **Initial Data Load:**
   - App will fetch users from API
   - Data stored in SQLite automatically
   - Status bar shows sync progress

2. **Theme Switching:**
   - Menu (⋮) → Theme → Select (Light/Dark/Custom)
   - App recreates with new theme
   - Close and reopen app → Theme persists

3. **View Mode Toggle:**
   - Menu (⋮) → Toggle View Mode
   - Switches between RecyclerView and ListView

4. **User Interactions:**
   - **Click** any user → View details
   - **Long press** any user → Context menu (Edit/Delete/View Details)
   - **Three-dot (⋮)** on card → Popup menu (Edit/Delete/Website)

5. **Edit User:**
   - Context menu or Popup menu → Edit
   - Change name → Save
   - User updated in database

6. **Delete User:**
   - Context menu or Popup menu → Delete
   - Confirm deletion
   - User removed from list and database

7. **View Website:**
   - User detail → View Website button
   - OR Popup menu → View Website
   - Opens WebView with user's website

8. **View Location:**
   - User detail → View on Map button
   - Opens OpenStreetMap in WebView

9. **Refresh Data:**
   - FAB (Floating Action Button) at bottom-right
   - OR Menu (⋮) → Refresh
   - Re-syncs data from API

10. **Offline Mode:**
    - Enable Airplane mode on device
    - Close and reopen app
    - Data loads from SQLite (offline storage)

11. **Logout:**
    - Menu (⋮) → Logout
    - Returns to login screen
    - Login state cleared (theme preserved)

12. **Rotation Test:**
    - Rotate device while on any screen
    - State should be preserved
    - No data loss or crashes

## Troubleshooting

### Gradle Sync Failed
- File → Invalidate Caches → Invalidate and Restart
- Delete `.gradle` folder and re-sync

### App Not Installing
- Build → Clean Project
- Build → Rebuild Project
- Uninstall previous version from device

### Network Error
- Check internet connection
- API might be temporarily down
- Try again after few minutes
- Offline mode still works with cached data

### Database Issues
- Settings → Apps → MAD Assignment 3 → Storage → Clear Data
- This resets database and preferences

## Project Structure Overview

```
MAD Assignment 3/
├── app/
│   ├── src/main/
│   │   ├── java/com/mujeeb/madassignment3/
│   │   │   ├── adapters/        # RecyclerView adapter
│   │   │   ├── database/        # SQLite helper
│   │   │   ├── models/          # Data models
│   │   │   ├── network/         # Retrofit API
│   │   │   ├── ui/              # Activities
│   │   │   └── utils/           # Helper classes
│   │   ├── res/
│   │   │   ├── layout/          # XML layouts
│   │   │   ├── menu/            # Menu XMLs
│   │   │   ├── values/          # Strings, colors, themes
│   │   │   └── xml/             # Config files
│   │   └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

## Key Files to Review

1. **MainActivity.kt** - Main screen with user list
2. **DatabaseHelper.kt** - SQLite CRUD operations
3. **UserAdapter.kt** - Custom RecyclerView adapter
4. **PreferencesManager.kt** - SharedPreferences handling
5. **ThemeManager.kt** - Theme switching logic
6. **RetrofitClient.kt** - API client setup

## Screenshots Checklist

For your submission document, capture:
1. ✅ Login screen
2. ✅ Main screen (RecyclerView mode)
3. ✅ Main screen (ListView mode)
4. ✅ Options menu
5. ✅ Theme variations (Light/Dark/Custom)
6. ✅ Context menu (long press)
7. ✅ Popup menu (three-dot)
8. ✅ User detail screen
9. ✅ WebView screen
10. ✅ Edit dialog
11. ✅ Delete confirmation
12. ✅ Offline mode status
13. ✅ Android Studio project structure
14. ✅ GitHub repository

## Git Commands

### First Time Setup
```bash
cd "d:\Mujeeb Projects\MAD Assignment 3"
git init
git add .
git commit -m "Initial commit: Complete MAD Assignment 3"
git branch -M main
git remote add origin https://github.com/mujeib101/mad-assignment-3.git
git push -u origin main
```

### Update Repository
```bash
git add .
git commit -m "Update: Description of changes"
git push
```

## Important Notes

1. **API Endpoint:** Uses JSONPlaceholder (https://jsonplaceholder.typicode.com/users)
2. **No Backend Required:** API is public and free
3. **Demo Login:** Any credentials work (validation is client-side only)
4. **Offline Mode:** Fully functional after first sync
5. **Data Persistence:** All data stored in SQLite
6. **Theme Persistence:** Saved in SharedPreferences

## Submission Checklist

- [ ] Source code pushed to GitHub
- [ ] README.md completed
- [ ] Screenshots captured
- [ ] Submission document prepared with:
  - [ ] Code screenshots from Android Studio
  - [ ] App screenshots from device/emulator
  - [ ] GitHub repository screenshot
  - [ ] Feature descriptions
  - [ ] Navigation flow diagram

## Contact & Support

- **Repository:** https://github.com/mujeib101/mad-assignment-3
- **Issues:** Use GitHub Issues for bug reports
- **Documentation:** See README.md for detailed information

---

**Happy Testing! 🚀**
