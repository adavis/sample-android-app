package info.adavis.sampleapp

import android.app.Application

import timber.log.Timber

class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Timber.i("Creating our Application")
    }

}
