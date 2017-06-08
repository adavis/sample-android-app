package info.adavis.sampleapp.jobs

import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest
import com.evernote.android.job.util.support.PersistableBundleCompat

import timber.log.Timber
import javax.inject.Inject

class DemoPeriodicJob @Inject constructor() : Job() {

    override fun onRunJob(params: Job.Params): Job.Result {
        val extras = params.extras
        val name = extras.getString(PARAM_NAME, "nobody")

        Timber.i("Hello from the periodic job, %s!", name)

        return Job.Result.SUCCESS
    }

    companion object {
        const val JOB_TAG = "demo_periodic_job"
        private val PARAM_NAME = "param_name"

        fun scheduleWithExtras(name: String) {
            val extras = PersistableBundleCompat()
            extras.putString(PARAM_NAME, name)

            JobRequest.Builder(DemoPeriodicJob.JOB_TAG)
                    .setPeriodic(JobRequest.MIN_INTERVAL)
                    .setExtras(extras)
                    .setUpdateCurrent(true)
                    .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                    .setRequirementsEnforced(true)
                    .build()
                    .schedule()
        }
    }

}
