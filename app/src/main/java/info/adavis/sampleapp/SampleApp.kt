package info.adavis.sampleapp

import android.app.Application
import com.evernote.android.job.JobCreator
import com.evernote.android.job.JobManager
import info.adavis.sampleapp.jobs.DemoJob
import info.adavis.sampleapp.jobs.DemoPeriodicJob
import timber.log.Timber
import javax.inject.Inject

class SampleApp : Application() {

    @Inject lateinit var jobCreator : JobCreator

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        DaggerAppComponent.create().inject(this)

        JobManager.create(this).addJobCreator(jobCreator)

        DemoJob.scheduleSimple()
        DemoPeriodicJob.scheduleWithExtras("Karen")
    }

}
