package com.autumnsun.randomuser.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.autumnsun.randomuser.R
import com.autumnsun.randomuser.model.Results
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter (var userList:List<Results>): RecyclerView.Adapter<UserAdapter.ViewHolder>(){
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val userName = itemView.findViewById<TextView>(R.id.item_username)
        val email = itemView.findViewById<TextView>(R.id.item_user_email)
        val userImage=itemView.findViewById<CircleImageView>(R.id.user_item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =userList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userName.text="${userList[position].name.first} ${userList[position].name.last}"
        holder.email.text=userList[position].email
        Picasso.get().load(userList[position].picture.large).into(holder.userImage)
    }

}