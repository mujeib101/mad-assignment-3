package com.mujeeb.madassignment3.utils

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.mujeeb.madassignment3.R

/**
 * ThemeManager - Manages application theme switching
 * Supports Light, Dark, and Custom themes
 */
object ThemeManager {
    
    /**
     * Apply theme to activity
     */
    fun applyTheme(activity: AppCompatActivity, theme: String) {
        when (theme) {
            PreferencesManager.THEME_LIGHT -> {
                activity.setTheme(R.style.Theme_MADAssignment3)
            }
            PreferencesManager.THEME_DARK -> {
                activity.setTheme(R.style.Theme_MADAssignment3_Dark)
            }
            PreferencesManager.THEME_CUSTOM -> {
                activity.setTheme(R.style.Theme_MADAssignment3_Custom)
            }
        }
    }
    
    /**
     * Change theme at runtime
     */
    fun changeTheme(activity: Activity, newTheme: String) {
        val prefsManager = PreferencesManager(activity)
        prefsManager.currentTheme = newTheme
        activity.recreate()
    }
}
