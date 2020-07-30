package com.manan.test.kotlinnews.presentation

import android.app.ActivityManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.manan.test.kotlinnews.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecentAppsTitleColor()
    }

    private fun setupRecentAppsTitleColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val bm = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
            val taskDesc = ActivityManager.TaskDescription(getString(R.string.app_name), bm, ContextCompat.getColor(this, android.R.color.white))
            this.setTaskDescription(taskDesc)
        }
    }

}
