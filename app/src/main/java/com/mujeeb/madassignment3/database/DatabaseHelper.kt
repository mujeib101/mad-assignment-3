package com.mujeeb.madassignment3.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mujeeb.madassignment3.models.Address
import com.mujeeb.madassignment3.models.Company
import com.mujeeb.madassignment3.models.Geo
import com.mujeeb.madassignment3.models.User

/**
 * DatabaseHelper - SQLite database handler
 * Implements CRUD operations for User data
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    
    companion object {
        private const val DATABASE_NAME = "mad_assignment3.db"
        private const val DATABASE_VERSION = 1
        
        // Users Table
        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_WEBSITE = "website"
        private const val COLUMN_ADDRESS_STREET = "address_street"
        private const val COLUMN_ADDRESS_SUITE = "address_suite"
        private const val COLUMN_ADDRESS_CITY = "address_city"
        private const val COLUMN_ADDRESS_ZIPCODE = "address_zipcode"
        private const val COLUMN_ADDRESS_LAT = "address_lat"
        private const val COLUMN_ADDRESS_LNG = "address_lng"
        private const val COLUMN_COMPANY_NAME = "company_name"
        private const val COLUMN_COMPANY_CATCHPHRASE = "company_catchphrase"
        private const val COLUMN_COMPANY_BS = "company_bs"
    }
    
    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_USERNAME TEXT NOT NULL,
                $COLUMN_EMAIL TEXT NOT NULL,
                $COLUMN_PHONE TEXT,
                $COLUMN_WEBSITE TEXT,
                $COLUMN_ADDRESS_STREET TEXT,
                $COLUMN_ADDRESS_SUITE TEXT,
                $COLUMN_ADDRESS_CITY TEXT,
                $COLUMN_ADDRESS_ZIPCODE TEXT,
                $COLUMN_ADDRESS_LAT TEXT,
                $COLUMN_ADDRESS_LNG TEXT,
                $COLUMN_COMPANY_NAME TEXT,
                $COLUMN_COMPANY_CATCHPHRASE TEXT,
                $COLUMN_COMPANY_BS TEXT
            )
        """.trimIndent()
        
        db.execSQL(createUsersTable)
    }
    
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }
    
    /**
     * INSERT - Add a user to database
     */
    fun insertUser(user: User): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ID, user.id)
            put(COLUMN_NAME, user.name)
            put(COLUMN_USERNAME, user.username)
            put(COLUMN_EMAIL, user.email)
            put(COLUMN_PHONE, user.phone)
            put(COLUMN_WEBSITE, user.website)
            put(COLUMN_ADDRESS_STREET, user.address?.street)
            put(COLUMN_ADDRESS_SUITE, user.address?.suite)
            put(COLUMN_ADDRESS_CITY, user.address?.city)
            put(COLUMN_ADDRESS_ZIPCODE, user.address?.zipcode)
            put(COLUMN_ADDRESS_LAT, user.address?.geo?.lat)
            put(COLUMN_ADDRESS_LNG, user.address?.geo?.lng)
            put(COLUMN_COMPANY_NAME, user.company?.name)
            put(COLUMN_COMPANY_CATCHPHRASE, user.company?.catchPhrase)
            put(COLUMN_COMPANY_BS, user.company?.bs)
        }
        
        return db.insertWithOnConflict(TABLE_USERS, null, values, SQLiteDatabase.CONFLICT_REPLACE)
    }
    
    /**
     * INSERT MULTIPLE - Batch insert users
     */
    fun insertUsers(users: List<User>) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            users.forEach { user ->
                insertUser(user)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
    
    /**
     * SELECT - Get all users
     */
    fun getAllUsers(): List<User> {
        val users = mutableListOf<User>()
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_USERS,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_NAME ASC"
        )
        
        if (cursor.moveToFirst()) {
            do {
                users.add(getUserFromCursor(cursor))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return users
    }
    
    /**
     * SELECT - Get user by ID
     */
    fun getUserById(userId: Int): User? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            null,
            "$COLUMN_ID = ?",
            arrayOf(userId.toString()),
            null,
            null,
            null
        )
        
        var user: User? = null
        if (cursor.moveToFirst()) {
            user = getUserFromCursor(cursor)
        }
        cursor.close()
        return user
    }
    
    /**
     * UPDATE - Update user information
     */
    fun updateUser(user: User): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, user.name)
            put(COLUMN_USERNAME, user.username)
            put(COLUMN_EMAIL, user.email)
            put(COLUMN_PHONE, user.phone)
            put(COLUMN_WEBSITE, user.website)
            put(COLUMN_ADDRESS_STREET, user.address?.street)
            put(COLUMN_ADDRESS_SUITE, user.address?.suite)
            put(COLUMN_ADDRESS_CITY, user.address?.city)
            put(COLUMN_ADDRESS_ZIPCODE, user.address?.zipcode)
            put(COLUMN_ADDRESS_LAT, user.address?.geo?.lat)
            put(COLUMN_ADDRESS_LNG, user.address?.geo?.lng)
            put(COLUMN_COMPANY_NAME, user.company?.name)
            put(COLUMN_COMPANY_CATCHPHRASE, user.company?.catchPhrase)
            put(COLUMN_COMPANY_BS, user.company?.bs)
        }
        
        return db.update(TABLE_USERS, values, "$COLUMN_ID = ?", arrayOf(user.id.toString()))
    }
    
    /**
     * DELETE - Delete user by ID
     */
    fun deleteUser(userId: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_USERS, "$COLUMN_ID = ?", arrayOf(userId.toString()))
    }
    
    /**
     * DELETE ALL - Clear all users
     */
    fun deleteAllUsers(): Int {
        val db = writableDatabase
        return db.delete(TABLE_USERS, null, null)
    }
    
    /**
     * COUNT - Get total number of users
     */
    fun getUserCount(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_USERS", null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        return count
    }
    
    /**
     * Helper method to convert Cursor to User object
     */
    private fun getUserFromCursor(cursor: Cursor): User {
        val address = if (cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS_CITY)) != null) {
            Address(
                street = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS_STREET)) ?: "",
                suite = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS_SUITE)) ?: "",
                city = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS_CITY)) ?: "",
                zipcode = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS_ZIPCODE)) ?: "",
                geo = Geo(
                    lat = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS_LAT)) ?: "",
                    lng = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS_LNG)) ?: ""
                )
            )
        } else null
        
        val company = if (cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMPANY_NAME)) != null) {
            Company(
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMPANY_NAME)) ?: "",
                catchPhrase = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMPANY_CATCHPHRASE)) ?: "",
                bs = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMPANY_BS)) ?: ""
            )
        } else null
        
        return User(
            id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
            name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
            username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
            email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
            phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)) ?: "",
            website = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEBSITE)) ?: "",
            address = address,
            company = company
        )
    }
}
