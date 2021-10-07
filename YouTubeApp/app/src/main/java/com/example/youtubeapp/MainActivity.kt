package com.example.youtubeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MainActivity : AppCompatActivity() {
    private val videos: Array<Array<String>> = arrayOf(
        arrayOf("Sunday lesson", "6fvXhCffLaY"),
        arrayOf("Monday lesson", "VZnLB-q55tw"),
        arrayOf("Tuesday lesson", "pESK3Vp_pCY"),
        arrayOf("Wednesday", "YC1uPCY3_-0"),
        arrayOf("Thursday", "Hz94iHJla-E")
    )


    private lateinit var player: YouTubePlayer
    lateinit var myVideo: YouTubePlayerView
    lateinit var rvMain: RecyclerView
    lateinit var clMain: ConstraintLayout
    private var currentVideo = 0
    private var timeStamp = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myVideo = findViewById(R.id.youtube_player_view)
        rvMain = findViewById(R.id.rvMain)
        clMain = findViewById(R.id.clMain)

        // btnArray[0] = playVideo("YE7VzlLtp-4")

        val layoutManager = LinearLayoutManager(this)
        myVideo.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                myVideo.getPlayerUiController().showFullscreenButton(true)
                player = youTubePlayer
                player.loadVideo(videos[currentVideo][1], timeStamp)
                rvMain.adapter = ViedoAdapter(videos, player)
                rvMain.layoutManager = LinearLayoutManager(this@MainActivity)

            }
        })
        //playVideo("CiFyTc1SwPw")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentVidio", currentVideo)
        outState.putFloat("time", timeStamp)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentVideo = savedInstanceState.getInt("currentVidio")
        timeStamp = savedInstanceState.getFloat("time")

        // counter.text = count.toString()
    }
}
//    fun  playVideo(id: String){
//
//        myVideo.getPlayerUiController().showFullscreenButton(true)
//        myVideo.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
//            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
//                val videoId = id
//                youTubePlayer.cueVideo(videoId, 0f)
//            }
//        })
//    }
    //data class myButton(val name: String , val id: String)
//
//    object myVideoLliat {
//        val myBtn = arrayListOf<myButton>(myButton("number Game" , "YE7VzlLtp-4"),
//            myButton("phrase Game" , "YE7VzlLtp-4"),
//            myButton("movie Game" , "YE7VzlLtp-4"),
//            myButton("hang man Game" , "YE7VzlLtp-4"),
//            myButton("T/F Game" , "YE7VzlLtp-4"))
//
//
//    }
