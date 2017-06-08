package info.adavis.sampleapp

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import info.adavis.sampleapp.jobs.DemoJob
import info.adavis.sampleapp.jobs.DemoPeriodicJob
import info.adavis.sampleapp.jobs.SampleAppJobCreator
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds @IntoMap
    @StringKey(DemoJob.JOB_TAG)
    internal abstract fun bindDemoJob(job: DemoJob): Job

    @Binds @IntoMap
    @StringKey(DemoPeriodicJob.JOB_TAG)
    internal abstract fun bindDemoPeriodicJob(job: DemoPeriodicJob): Job

    @Binds
    @Singleton
    internal abstract fun bindJobCreator(jobCreator: SampleAppJobCreator): JobCreator

}