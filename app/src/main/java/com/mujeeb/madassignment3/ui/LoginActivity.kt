package com.mujeeb.madassignment3.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mujeeb.madassignment3.R
import com.mujeeb.madassignment3.utils.PreferencesManager
import com.mujeeb.madassignment3.utils.ThemeManager

/**
 * LoginActivity - Authentication screen
 * Handles login state and theme persistence
 */
class LoginActivity : AppCompatActivity() {
    
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var cbRememberMe: CheckBox
    private lateinit var prefsManager: PreferencesManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply saved theme before setting content view
        prefsManager = PreferencesManager(this)
        ThemeManager.applyTheme(this, prefsManager.currentTheme)
        
        super.onCreate(savedInstanceState)
        
        // Check if already logged in
        if (prefsManager.isLoggedIn) {
            navigateToMain()
            return
        }
        
        setContentView(R.layout.activity_login)
        
        initializeViews()
        setupListeners()
    }
    
    private fun initializeViews() {
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        cbRememberMe = findViewById(R.id.cbRememberMe)
    }
    
    private fun setupListeners() {
        btnLogin.setOnClickListener {
            performLogin()
        }
    }
    
    /**
     * Perform login validation
     */
    private fun performLogin() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()
        
        // Input validation
        if (username.isEmpty()) {
            etUsername.error = "Username is required"
            etUsername.requestFocus()
            return
        }
        
        if (password.isEmpty()) {
            etPassword.error = "Password is required"
            etPassword.requestFocus()
            return
        }
        
        if (password.length < 4) {
            etPassword.error = "Password must be at least 4 characters"
            etPassword.requestFocus()
            return
        }
        
        // Simple authentication (for demo purposes)
        // In production, validate against backend
        if (username.length >= 3) {
            // Save login state
            prefsManager.isLoggedIn = true
            prefsManager.username = username
            
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
            navigateToMain()
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
        }
    }
    
    /**
     * Navigate to MainActivity
     */
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    /**
     * Handle configuration changes (rotation)
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("username", etUsername.text.toString())
    }
    
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        etUsername.setText(savedInstanceState.getString("username", ""))
    }
}
