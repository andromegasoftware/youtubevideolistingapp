package com.company.youtubelisting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_row.view.*

class adapter_class(var list:ArrayList<Category>, val clickListener: (Category)->Unit):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var my_view = LayoutInflater.from(parent.context).inflate(R.layout.category_row, parent, false)

        return CategoryViewHolder(my_view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as CategoryViewHolder).bind(list[position], clickListener)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    class CategoryViewHolder(view: View):RecyclerView.ViewHolder(view){

        var category_picture = view.imageView
        var category_name = view.textViewNameCategory
        fun bind(category:Category, clickListener: (Category) -> Unit){
            //link category name to textview and imageview
            category_name.text=category.name

            if (category.picture != null){
                var pictureURL = category.picture!!.url
                Picasso.get().load(pictureURL).into(category_picture)
            }

            itemView.setOnClickListener { clickListener(category) }
        }
    }
}