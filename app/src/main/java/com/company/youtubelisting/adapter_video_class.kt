package com.company.youtubelisting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_row.view.*
import kotlinx.android.synthetic.main.video_row.view.*

class adapter_video_class(var list:ArrayList<Video>, val clickListener: (Video)->Unit):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var my_view = LayoutInflater.from(parent.context).inflate(R.layout.video_row, parent, false)

        return VideoViewHolder(my_view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as VideoViewHolder).bind(list[position], clickListener)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    class VideoViewHolder(view: View):RecyclerView.ViewHolder(view){

        var video_picture = view.imageViewVideo
        var video_title = view.textViewVideoName
        fun bind(video: Video, clickListener: (Video) -> Unit){
            //link category name to textview and imageview
            video_title.text=video.title

            if (video.youtubeId != null){
                var videoPictureURL = "https://img.youtube.com/vi/" + video.youtubeId + "/0.jpg"
                Picasso.get().load(videoPictureURL).into(video_picture)
            }

            itemView.setOnClickListener { clickListener(video) }
        }
    }
}