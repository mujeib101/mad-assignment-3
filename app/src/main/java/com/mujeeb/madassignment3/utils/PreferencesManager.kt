package com.mujeeb.madassignment3.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * PreferencesManager - Handles all SharedPreferences operations
 * Manages user authentication state and theme preferences
 */
class PreferencesManager(context: Context) {
    
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    companion object {
        private const val PREFS_NAME = "MADAssignment3Prefs"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USERNAME = "username"
        private const val KEY_THEME = "theme"
        private const val KEY_LAST_SYNC = "lastSync"
        
        const val THEME_LIGHT = "light"
        const val THEME_DARK = "dark"
        const val THEME_CUSTOM = "custom"
    }
    
    /**
     * Authentication Management
     */
    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()
    
    var username: String?
        get() = sharedPreferences.getString(KEY_USERNAME, null)
        set(value) = sharedPreferences.edit().putString(KEY_USERNAME, value).apply()
    
    /**
     * Theme Management
     */
    var currentTheme: String
        get() = sharedPreferences.getString(KEY_THEME, THEME_LIGHT) ?: THEME_LIGHT
        set(value) = sharedPreferences.edit().putString(KEY_THEME, value).apply()
    
    /**
     * Data Sync Management
     */
    var lastSyncTime: Long
        get() = sharedPreferences.getLong(KEY_LAST_SYNC, 0)
        set(value) = sharedPreferences.edit().putLong(KEY_LAST_SYNC, value).apply()
    
    /**
     * Clear all preferences (logout)
     */
    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }
    
    /**
     * Logout but preserve theme preference
     */
    fun logout() {
        val theme = currentTheme
        clearAll()
        currentTheme = theme
    }
}
