package com.manan.test.kotlinnews.presentation.list.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.manan.test.kotlinnews.R
import com.manan.test.kotlinnews.data.entity.Thing
import com.manan.test.kotlinnews.databinding.ViewSubredditBinding
import com.manan.test.kotlinnews.domain.utils.CircleTransformation

class ItemSubredditAdapter(
    internal var items: MutableList<Thing>,
    private val clickListener: OnItemClickListener,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ViewSubredditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderItem(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolderBody = holder as ViewHolderItem
        val thing = items[position]
        val data = thing.data

        val image = thing.data?.thumbnail
        if (image != null && image.isNotBlank()) {
            Picasso.with(context)
                .load(image)
                .transform(CircleTransformation())
                .into(viewHolderBody.binding.itemIcon)
        } else {
            Picasso.with(context)
                .load(R.mipmap.ic_launcher_round)
                .into(viewHolderBody.binding.itemIcon)
        }

        viewHolderBody.binding.itemName.text = "${data?.title.orEmpty()} : ${data?.title.orEmpty()}"
        viewHolderBody.binding.itemDescription.text = data?.selfText.orEmpty()
        viewHolderBody.itemView.setOnClickListener { view -> clickListener.onClick(view, thing) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun addAll(list: List<Thing>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun getItems(): List<Thing> {
        return items
    }

    fun setItems(items: List<Thing>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(view: View, thing: Thing)
    }

    internal class ViewHolderItem(var binding: ViewSubredditBinding) : RecyclerView.ViewHolder(binding.root)
}
