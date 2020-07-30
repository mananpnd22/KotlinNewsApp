package com.manan.test.kotlinnews.presentation

interface BasePresenter {

    fun resume() {}

    fun pause() {}

    fun destroy() {}

    fun setView(view: BaseView) {}

}
