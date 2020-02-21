package com.example.networkinggithubproject.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.networkinggithubproject.R
import com.example.networkinggithubproject.modelRepository.Repository
import kotlinx.android.synthetic.main.repository_item.view.*

class ProfileAdapter(var repoList: ArrayList<Repository>) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder((LayoutInflater.from(parent.context).inflate(R.layout.repository_item, parent, false)))

    override fun getItemCount(): Int = repoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.populateItem(repoList[position])
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        fun populateItem(repo : Repository)
        {
            itemView.tvName.text = repo.full_name
            itemView.tvUrl.text = repo.url
            itemView.tvSize.text = repo.size.toString()
        }

    }
}