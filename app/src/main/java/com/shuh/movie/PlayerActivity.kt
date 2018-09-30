package com.shuh.movie

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.View
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.tbruyelle.rxpermissions2.RxPermissions
import java.io.File
import java.util.*
import cn.jzvd.JZUserActionStd
import cn.jzvd.JZUserAction
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN






class PlayerActivity : AppCompatActivity() {

    private lateinit var filePath: String
    private lateinit var videoView: JzvdStd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val decorView = window.decorView //得到当前界面的装饰视图
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN //设置系统UI元素的可见性
//        supportActionBar!!.hide() //隐藏标题栏
        
        filePath = intent.getStringExtra("PlayerActivity.filePath")
        if (TextUtils.isEmpty(filePath)) return
        videoView = findViewById(R.id.videoView)
        Jzvd.setJzUserAction { type, url, screen, objects ->
            when (type) {
                JZUserAction.ON_CLICK_START_ICON -> Log.i("USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (if (objects.size === 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen)
                JZUserAction.ON_CLICK_START_ERROR -> Log.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (if (objects.size === 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen)
                JZUserAction.ON_CLICK_START_AUTO_COMPLETE -> Log.i("USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (if (objects.size === 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen)
                JZUserAction.ON_CLICK_PAUSE -> Log.i("USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (if (objects.size === 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen)
                JZUserAction.ON_CLICK_RESUME -> Log.i("USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (if (objects.size === 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen)
                JZUserAction.ON_SEEK_POSITION -> Log.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (if (objects.size === 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen)
                JZUserAction.ON_AUTO_COMPLETE -> Log.i("USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (if (objects.size === 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen)
                JZUserAction.ON_ENTER_FULLSCREEN -> Log.i("USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (if (objects.size === 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen)
                JZUserAction.ON_QUIT_FULLSCREEN -> Log.i("USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (if (objects.size === 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen)
                JZUserAction.ON_ENTER_TINYSCREEN -> Log.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (if (objects.size === 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen)
                JZUserAction.ON_QUIT_TINYSCREEN -> Log.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (if (objects.size === 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen)
                JZUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME -> Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (if (objects.size === 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen)
                JZUserAction.ON_TOUCH_SCREEN_SEEK_POSITION -> Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (if (objects.size === 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen)

                JZUserActionStd.ON_CLICK_START_THUMB -> Log.i("USER_EVENT", "ON_CLICK_START_THUMB" + " title is : " + (if (objects.size === 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen)
                JZUserActionStd.ON_CLICK_BLANK -> Log.i("USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (if (objects.size === 0) "" else objects[0]) + " url is : " + url + " screen is : " + screen)
                else -> Log.i("USER_EVENT", "unknow")
            }
        }
        RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe { granted ->
                    if (granted) {
                        val file = File(filePath)
                        videoView.setUp(file.toURI().path, file.name, Jzvd.SCREEN_WINDOW_FULLSCREEN)
                    }
                }
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

//    private fun vitamioSetup(){
//        videoView.setMediaController(MediaController(this))
//        videoView.requestFocus()
//        videoView.setOnPreparedListener { it.setPlaybackSpeed(1.0f) }
//        RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE)
//                .subscribe { granted ->
//                    if (granted) {
//                        videoView.setVideoPath(File(filePath).toURI().path)
//                        videoView.start()
////                        videoView.setUp(File(filePath).toURI().path, "aaa", 1)
//                    }
//                }
//    }
}
