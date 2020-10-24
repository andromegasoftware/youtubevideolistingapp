package com.company.youtubelisting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import kotlinx.android.synthetic.main.activity_play_video.*

class PlayVideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set screen mode to fullscreen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        //hide the action bar
        supportActionBar?.hide()
        setContentView(R.layout.activity_play_video)

        val videoYoutubeId = intent.getStringExtra("youtubeId")
        var videoUrl = "https://www.youtube.com/embed/" + videoYoutubeId

        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = object : WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                if (newProgress == 100)
                {
                    lineerlayoutPlayingVideos.visibility = View.GONE
                }
            }
        }
        webView.loadUrl(videoUrl)
    }
}