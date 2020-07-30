package com.manan.test.kotlinnews.domain.di

import com.manan.test.kotlinnews.presentation.details.DetailsActivity
import com.manan.test.kotlinnews.presentation.list.view.ListActivity
//import com.manan.test.kotlinnews.presentation.splash.view.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(target: ListActivity)

    //fun inject(target: SplashActivity)

    fun inject(target: DetailsActivity)
}
