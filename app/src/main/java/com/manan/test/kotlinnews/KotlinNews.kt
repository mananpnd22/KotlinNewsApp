package com.manan.test.kotlinnews

import androidx.multidex.MultiDexApplication
import com.crashlytics.android.BuildConfig

import com.crashlytics.android.Crashlytics
import com.squareup.leakcanary.LeakCanary
import com.manan.test.kotlinnews.data.logger.LoggerTree
import com.manan.test.kotlinnews.domain.di.AppComponent
import com.manan.test.kotlinnews.domain.di.AppModule
import com.manan.test.kotlinnews.domain.di.DaggerAppComponent


import io.fabric.sdk.android.Fabric
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


class KotlinNews : MultiDexApplication() {

    var appComponent: AppComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupCalligraphy()
        createAppComponent()
        setupLeakCanary()
    }

    private fun setupTimber() {
        Fabric.with(this, Crashlytics())
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(LoggerTree())
        }
    }

    private fun setupCalligraphy() {
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/Roboto-Regular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build()
        )
    }

    private fun setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }

    private fun createAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}