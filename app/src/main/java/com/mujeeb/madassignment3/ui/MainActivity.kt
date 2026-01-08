package com.mujeeb.madassignment3.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mujeeb.madassignment3.R
import com.mujeeb.madassignment3.adapters.UserAdapter
import com.mujeeb.madassignment3.database.DatabaseHelper
import com.mujeeb.madassignment3.models.User
import com.mujeeb.madassignment3.network.RetrofitClient
import com.mujeeb.madassignment3.utils.PreferencesManager
import com.mujeeb.madassignment3.utils.ThemeManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * MainActivity - Main screen displaying users
 * Implements Options Menu, Context Menu, RecyclerView, and offline mode
 */
class MainActivity : AppCompatActivity() {
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var listView: ListView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvStatus: TextView
    private lateinit var fabRefresh: FloatingActionButton
    
    private lateinit var userAdapter: UserAdapter
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var prefsManager: PreferencesManager
    
    private var users: List<User> = emptyList()
    private var isRecyclerViewMode = true
    private var selectedUser: User? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply saved theme
        prefsManager = PreferencesManager(this)
        ThemeManager.applyTheme(this, prefsManager.currentTheme)
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Initialize database
        dbHelper = DatabaseHelper(this)
        
        initializeViews()
        setupRecyclerView()
        setupListView()
        loadData()
        
        // Restore state if needed
        savedInstanceState?.let {
            isRecyclerViewMode = it.getBoolean("isRecyclerViewMode", true)
            toggleViewMode()
        }
    }
    
    private fun initializeViews() {
        recyclerView = findViewById(R.id.recyclerView)
        listView = findViewById(R.id.listView)
        progressBar = findViewById(R.id.progressBar)
        tvStatus = findViewById(R.id.tvStatus)
        fabRefresh = findViewById(R.id.fabRefresh)
        
        fabRefresh.setOnClickListener {
            fetchUsersFromApi()
        }
        
        // Register context menu for ListView
        registerForContextMenu(listView)
    }
    
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(
            users = emptyList(),
            onItemClick = { user ->
                openUserDetail(user)
            },
            onEditClick = { user ->
                showEditDialog(user)
            },
            onDeleteClick = { user ->
                showDeleteConfirmation(user)
            },
            onWebsiteClick = { user ->
                openWebsite(user)
            }
        )
        recyclerView.adapter = userAdapter
        
        // Register context menu for RecyclerView items
        registerForContextMenu(recyclerView)
    }
    
    private fun setupListView() {
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = arrayAdapter
        
        listView.setOnItemClickListener { _, _, position, _ ->
            if (position < users.size) {
                openUserDetail(users[position])
            }
        }
        
        listView.setOnItemLongClickListener { _, view, position, _ ->
            if (position < users.size) {
                selectedUser = users[position]
                view.showContextMenu()
                true
            } else {
                false
            }
        }
    }
    
    /**
     * Load data from database first, then fetch from API
     */
    private fun loadData() {
        lifecycleScope.launch {
            // Load from database first
            val localUsers = withContext(Dispatchers.IO) {
                dbHelper.getAllUsers()
            }
            
            if (localUsers.isNotEmpty()) {
                users = localUsers
                updateUI()
                tvStatus.text = "Loaded from offline storage (${users.size} users)"
            } else {
                tvStatus.text = "No local data. Pull to fetch from API."
            }
            
            // Try to fetch from API
            fetchUsersFromApi()
        }
    }
    
    /**
     * Fetch users from API
     */
    private fun fetchUsersFromApi() {
        lifecycleScope.launch {
            try {
                progressBar.visibility = View.VISIBLE
                tvStatus.text = "Fetching from API..."
                
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.getUsers()
                }
                
                if (response.isSuccessful && response.body() != null) {
                    val apiUsers = response.body()!!
                    
                    // Save to database
                    withContext(Dispatchers.IO) {
                        dbHelper.insertUsers(apiUsers)
                    }
                    
                    users = apiUsers
                    updateUI()
                    
                    prefsManager.lastSyncTime = System.currentTimeMillis()
                    tvStatus.text = "Successfully synced ${users.size} users"
                    Toast.makeText(this@MainActivity, "Data synced!", Toast.LENGTH_SHORT).show()
                } else {
                    tvStatus.text = "API error: ${response.code()}"
                    Toast.makeText(this@MainActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                tvStatus.text = "Network error: ${e.message}"
                Toast.makeText(this@MainActivity, "Network error. Using offline data.", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
    
    /**
     * Update UI with current users
     */
    private fun updateUI() {
        if (isRecyclerViewMode) {
            userAdapter.updateUsers(users)
        } else {
            val userNames = users.map { "${it.name} (${it.email})" }
            arrayAdapter.clear()
            arrayAdapter.addAll(userNames)
        }
    }
    
    /**
     * Toggle between RecyclerView and ListView
     */
    private fun toggleViewMode() {
        isRecyclerViewMode = !isRecyclerViewMode
        
        if (isRecyclerViewMode) {
            recyclerView.visibility = View.VISIBLE
            listView.visibility = View.GONE
        } else {
            recyclerView.visibility = View.GONE
            listView.visibility = View.VISIBLE
        }
        
        updateUI()
    }
    
    /**
     * Options Menu - Theme switching, logout, view mode
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_theme_light -> {
                ThemeManager.changeTheme(this, PreferencesManager.THEME_LIGHT)
                true
            }
            R.id.action_theme_dark -> {
                ThemeManager.changeTheme(this, PreferencesManager.THEME_DARK)
                true
            }
            R.id.action_theme_custom -> {
                ThemeManager.changeTheme(this, PreferencesManager.THEME_CUSTOM)
                true
            }
            R.id.action_toggle_view -> {
                toggleViewMode()
                true
            }
            R.id.action_refresh -> {
                fetchUsersFromApi()
                true
            }
            R.id.action_logout -> {
                showLogoutConfirmation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    /**
     * Context Menu - Edit and Delete
     */
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_context, menu)
    }
    
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val user = selectedUser ?: return super.onContextItemSelected(item)
        
        return when (item.itemId) {
            R.id.action_edit -> {
                showEditDialog(user)
                true
            }
            R.id.action_delete -> {
                showDeleteConfirmation(user)
                true
            }
            R.id.action_view_details -> {
                openUserDetail(user)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    
    /**
     * Open user detail activity
     */
    private fun openUserDetail(user: User) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra("USER_ID", user.id)
        startActivity(intent)
    }
    
    /**
     * Open website in WebView
     */
    private fun openWebsite(user: User) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("URL", "https://${user.website}")
        intent.putExtra("TITLE", "${user.name}'s Website")
        startActivity(intent)
    }
    
    /**
     * Show edit dialog
     */
    private fun showEditDialog(user: User) {
        val input = android.widget.EditText(this)
        input.setText(user.name)
        
        AlertDialog.Builder(this)
            .setTitle("Edit User Name")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val newName = input.text.toString().trim()
                if (newName.isNotEmpty()) {
                    updateUser(user, newName)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    /**
     * Update user in database
     */
    private fun updateUser(user: User, newName: String) {
        lifecycleScope.launch {
            val updatedUser = user.copy(name = newName)
            
            withContext(Dispatchers.IO) {
                dbHelper.updateUser(updatedUser)
            }
            
            // Reload data
            users = withContext(Dispatchers.IO) {
                dbHelper.getAllUsers()
            }
            updateUI()
            
            Toast.makeText(this@MainActivity, "User updated", Toast.LENGTH_SHORT).show()
        }
    }
    
    /**
     * Show delete confirmation
     */
    private fun showDeleteConfirmation(user: User) {
        AlertDialog.Builder(this)
            .setTitle("Delete User")
            .setMessage("Are you sure you want to delete ${user.name}?")
            .setPositiveButton("Delete") { _, _ ->
                deleteUser(user)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    /**
     * Delete user from database
     */
    private fun deleteUser(user: User) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                dbHelper.deleteUser(user.id)
            }
            
            // Reload data
            users = withContext(Dispatchers.IO) {
                dbHelper.getAllUsers()
            }
            updateUI()
            
            Toast.makeText(this@MainActivity, "User deleted", Toast.LENGTH_SHORT).show()
        }
    }
    
    /**
     * Show logout confirmation
     */
    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout") { _, _ ->
                performLogout()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    /**
     * Perform logout
     */
    private fun performLogout() {
        prefsManager.logout()
        
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    /**
     * Save instance state for configuration changes
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isRecyclerViewMode", isRecyclerViewMode)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }
}
