package com.mujeeb.madassignment3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mujeeb.madassignment3.R
import com.mujeeb.madassignment3.models.User

/**
 * UserAdapter - Custom RecyclerView Adapter for displaying users
 * Implements click listeners and popup menu
 */
class UserAdapter(
    private var users: List<User>,
    private val onItemClick: (User) -> Unit,
    private val onEditClick: (User) -> Unit,
    private val onDeleteClick: (User) -> Unit,
    private val onWebsiteClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvUserName)
        val tvEmail: TextView = itemView.findViewById(R.id.tvUserEmail)
        val tvPhone: TextView = itemView.findViewById(R.id.tvUserPhone)
        val tvCompany: TextView = itemView.findViewById(R.id.tvUserCompany)
        val btnMore: View = itemView.findViewById(R.id.btnMore)
        
        init {
            // Item click listener
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(users[position])
                }
            }
            
            // Context menu via long click
            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemView.showContextMenu()
                    true
                } else {
                    false
                }
            }
            
            // Popup menu
            btnMore.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    showPopupMenu(it, users[position])
                }
            }
        }
        
        private fun showPopupMenu(view: View, user: User) {
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.menuInflater.inflate(R.menu.menu_user_popup, popupMenu.menu)
            
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_edit -> {
                        onEditClick(user)
                        true
                    }
                    R.id.action_delete -> {
                        onDeleteClick(user)
                        true
                    }
                    R.id.action_website -> {
                        onWebsiteClick(user)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.tvName.text = user.name
        holder.tvEmail.text = user.email
        holder.tvPhone.text = user.phone
        holder.tvCompany.text = user.company?.name ?: "No Company"
    }
    
    override fun getItemCount(): Int = users.size
    
    /**
     * Update adapter data
     */
    fun updateUsers(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged()
    }
    
    /**
     * Remove user at position
     */
    fun removeUser(position: Int) {
        if (position >= 0 && position < users.size) {
            val mutableList = users.toMutableList()
            mutableList.removeAt(position)
            users = mutableList
            notifyItemRemoved(position)
        }
    }
}
