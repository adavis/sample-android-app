package info.adavis.sampleapp.jobs

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator
import javax.inject.Inject
import javax.inject.Provider

class SampleAppJobCreator @Inject constructor(
        val jobs: @JvmSuppressWildcards Map<String, Provider<Job>>
) : JobCreator {

    override fun create(tag: String): Job? {
        return jobs[tag]?.get()
    }

}
