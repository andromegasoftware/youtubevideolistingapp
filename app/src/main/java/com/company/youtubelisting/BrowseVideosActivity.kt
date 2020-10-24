package com.company.youtubelisting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_browse_videos.*
import kotlinx.android.synthetic.main.activity_main.*

class BrowseVideosActivity : AppCompatActivity() {

    var videoList = ArrayList<Video>()

    var selectedCategoryObjectId = ""
    var adp = adapter_video_class(videoList, {videoItem:Video->videoClickListener(videoItem)})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_videos)

        setTitle(intent.getStringExtra(("selectedCategoryName")))
        selectedCategoryObjectId = intent.getStringExtra("selectedCategoryObjectId").toString()

        //Toast.makeText(this, "Selected video category object id " + selectedCategoryObjectId, Toast.LENGTH_LONG).show()
        recyclerViewVideos.layoutManager = LinearLayoutManager(this)
        recyclerViewVideos.adapter = adp
        loadVideos()
    }

    fun loadVideos(){
        lineerlayoutLoadingVideos.visibility = View.VISIBLE     //progressbar will appear when loading the categories
        val query = ParseQuery<ParseObject>("video")
        //query.orderByAscending("name")

        if (selectedCategoryObjectId != null){
            val categoryPointer = ParseObject.createWithoutData("category", selectedCategoryObjectId)
            query.whereEqualTo("category", categoryPointer)
        }
        query.findInBackground { list,e ->
            lineerlayoutLoadingVideos.visibility = View.GONE     //progressbar will be gone when loading the categories
            if (e==null){
                //no error
                Log.d("browse","there is no error")
                if (list.size>0){
                    //there is categories retrieved
                    Log.d("browse","there is videos retrieved " + list.size)
                    for (video in list)
                    {
                        videoList.add(Video(video.objectId, video.get("youtubeId").toString(), video.get("title").toString()))
                    }
                    adp.notifyDataSetChanged()
                    Log.d("browse","videolist content " + videoList.toString())
                    var namesofLoadedVideos = ""
                    for (c in videoList){
                        namesofLoadedVideos += c.title+"\n"
                    }
                    //category_act_txt.text = namesofLoadedCategories
                    Log.d("browse","titles of videos retrieved " + videoList.toString())
                }
                else{
                    //there is no categories in app backend
                    videoError.visibility = View.VISIBLE
                    textViewError2.text = "There is no category in this name"
                    Log.d("browse", "there is no video in backend")

                }
            }
            else{
                //there is error
                Log.d("browse", "there is an error")
                videoError.visibility = View.VISIBLE
                textViewError2.text = "There is an error. please check your internet connection"
                //buttonRetry.visibility = View.VISIBLE
                buttonRetry2.setOnClickListener {
                    loadVideos()
                    videoError.visibility = View.GONE
                }
            }

        }
    }

    fun videoClickListener(video: Video) {

        val intent = Intent(this, PlayVideoActivity::class.java)
        intent.putExtra("youtubeId", video.youtubeId)
        startActivity(intent)
    }
}