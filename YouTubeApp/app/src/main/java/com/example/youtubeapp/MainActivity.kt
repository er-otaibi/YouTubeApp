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
      
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentVidio", currentVideo)
        outState.putFloat("time", timeStamp)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentVideo = savedInstanceState.getInt("currentVidio" , 0 )
        timeStamp = savedInstanceState.getFloat("time" , 0f )

        // counter.text = count.toString()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            myVideo.enterFullScreen()
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            myVideo.exitFullScreen()
        }
    }



    private fun checkInternet(){
        if(!connectedToInternet()){
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Internet Connection Not Found")
                .setPositiveButton("RETRY"){_, _ -> checkInternet()}
                .show()
        }
    }

    private fun connectedToInternet(): Boolean{
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}

