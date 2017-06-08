package info.adavis.sampleapp.jobs

import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest
import com.evernote.android.job.util.support.PersistableBundleCompat

import java.util.concurrent.TimeUnit

import timber.log.Timber
import javax.inject.Inject

class DemoJob @Inject constructor() : Job() {

    override fun onRunJob(params: Job.Params): Job.Result {
        val extras = params.extras
        val name = extras.getString(PARAM_NAME, "nobody")

        Timber.i("Hello, %s!", name)

        return Job.Result.SUCCESS
    }

    companion object {
        const val JOB_TAG = "demo_job"
        private val PARAM_NAME = "param_name"

        fun scheduleSimple() {
            JobRequest.Builder(DemoJob.JOB_TAG)
                    .setExecutionWindow(TimeUnit.MINUTES.toMillis(2), TimeUnit.MINUTES.toMillis(5))
                    .build()
                    .schedule()
        }
    }

}
