package com.mujeeb.madassignment3.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mujeeb.madassignment3.R
import com.mujeeb.madassignment3.database.DatabaseHelper
import com.mujeeb.madassignment3.models.User
import com.mujeeb.madassignment3.utils.PreferencesManager
import com.mujeeb.madassignment3.utils.ThemeManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * UserDetailActivity - Display detailed user information
 */
class UserDetailActivity : AppCompatActivity() {
    
    private lateinit var tvName: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvWebsite: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvCompany: TextView
    private lateinit var btnViewWebsite: Button
    private lateinit var btnViewMap: Button
    
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var prefsManager: PreferencesManager
    private var currentUser: User? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        prefsManager = PreferencesManager(this)
        ThemeManager.applyTheme(this, prefsManager.currentTheme)
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "User Details"
        
        dbHelper = DatabaseHelper(this)
        
        initializeViews()
        loadUserData()
    }
    
    private fun initializeViews() {
        tvName = findViewById(R.id.tvDetailName)
        tvUsername = findViewById(R.id.tvDetailUsername)
        tvEmail = findViewById(R.id.tvDetailEmail)
        tvPhone = findViewById(R.id.tvDetailPhone)
        tvWebsite = findViewById(R.id.tvDetailWebsite)
        tvAddress = findViewById(R.id.tvDetailAddress)
        tvCompany = findViewById(R.id.tvDetailCompany)
        btnViewWebsite = findViewById(R.id.btnViewWebsite)
        btnViewMap = findViewById(R.id.btnViewMap)
        
        btnViewWebsite.setOnClickListener {
            currentUser?.let { user ->
                openWebsite(user.website)
            }
        }
        
        btnViewMap.setOnClickListener {
            currentUser?.let { user ->
                user.address?.geo?.let { geo ->
                    openMap(geo.lat, geo.lng)
                }
            }
        }
    }
    
    private fun loadUserData() {
        val userId = intent.getIntExtra("USER_ID", -1)
        if (userId == -1) {
            finish()
            return
        }
        
        lifecycleScope.launch {
            val user = withContext(Dispatchers.IO) {
                dbHelper.getUserById(userId)
            }
            
            if (user != null) {
                currentUser = user
                displayUserData(user)
            } else {
                finish()
            }
        }
    }
    
    private fun displayUserData(user: User) {
        tvName.text = user.name
        tvUsername.text = "@${user.username}"
        tvEmail.text = user.email
        tvPhone.text = user.phone
        tvWebsite.text = user.website
        
        // Address
        val address = user.address?.let {
            "${it.street}, ${it.suite}\n${it.city}, ${it.zipcode}"
        } ?: "No address available"
        tvAddress.text = address
        
        // Company
        val company = user.company?.let {
            "${it.name}\n${it.catchPhrase}\n${it.bs}"
        } ?: "No company information"
        tvCompany.text = company
    }
    
    private fun openWebsite(website: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("URL", "https://$website")
        intent.putExtra("TITLE", "Website")
        startActivity(intent)
    }
    
    private fun openMap(lat: String, lng: String) {
        val mapUrl = "https://www.openstreetmap.org/?mlat=$lat&mlon=$lng#map=15/$lat/$lng"
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("URL", mapUrl)
        intent.putExtra("TITLE", "Location")
        startActivity(intent)
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    
    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }
}
