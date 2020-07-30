package com.manan.test.kotlinnews.domain.di

import android.content.Context
import androidx.databinding.library.BuildConfig
import com.manan.test.kotlinnews.KotlinNews
import com.manan.test.kotlinnews.data.api.RedditAPI
import com.manan.test.kotlinnews.domain.utils.Constants
import com.manan.test.kotlinnews.domain.utils.Utils
import com.manan.test.kotlinnews.presentation.list.ListContract
import com.manan.test.kotlinnews.presentation.list.presenter.ListPresenter
//import com.manan.test.kotlinnews.presentation.splash.SplashContract
//import com.manan.test.kotlinnews.presentation.splash.presenter.SplashPresenter
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(private val application: KotlinNews) {

    @Provides
    @Singleton
    internal fun provideApplicationContext(): Context {
        return this.application
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    @Provides
    @Named("offlineCacheInterceptor")
    fun provideOfflineCacheInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()

            if (!Utils.hasNetwork(context)) {
                val cacheControl = CacheControl.Builder()
                    .maxStale(2, TimeUnit.DAYS)
                    .build()

                request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
            }

            chain.proceed(request)
        }
    }

    @Provides
    @Named("cacheInterceptor")
    fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            val cacheControl = CacheControl.Builder()
                .maxAge(2, TimeUnit.HOURS)
                .build()

            response.newBuilder()
                .header(CACHE_CONTROL, cacheControl.toString())
                .build()
        }
    }

    @Provides
    fun provideCache(context: Context): Cache {
        return Cache(File(context.cacheDir, "http-cache"), (10 * 1024 * 1024).toLong())
    }

    @Provides
    fun provideCapableHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor,
                                 cache: Cache,
                                 @Named("cacheInterceptor") cacheInterceptor: Interceptor,
                                 @Named("offlineCacheInterceptor") offlineCacheInterceptor: Interceptor): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .cache(cache)
            .addNetworkInterceptor(cacheInterceptor)
            .addInterceptor(offlineCacheInterceptor)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideBookService(retrofit: Retrofit): RedditAPI {
        return retrofit.create(RedditAPI::class.java)
    }

//    @Provides
//    fun provideSplashPresenter(): SplashContract.Presenter {
//        return SplashPresenter()
//    }

    @Provides
    fun provideAppListPresenter(redditAPI: RedditAPI): ListContract.Presenter {
        return ListPresenter(redditAPI)
    }

    companion object {
        private const val CACHE_CONTROL = "Cache-Control"
    }
}