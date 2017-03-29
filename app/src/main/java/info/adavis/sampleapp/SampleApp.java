package info.adavis.sampleapp;

import android.app.Application;

import com.evernote.android.job.JobManager;

import info.adavis.sampleapp.jobs.DemoJob;
import info.adavis.sampleapp.jobs.DemoPeriodicJob;
import info.adavis.sampleapp.jobs.SampleAppJobCreator;
import timber.log.Timber;

public class SampleApp extends Application
{

    @Override
    public void onCreate()
    {
        super.onCreate();

        if (BuildConfig.DEBUG)
        {
            Timber.plant(new Timber.DebugTree());
        }

        JobManager.create(this).addJobCreator(new SampleAppJobCreator());

        DemoJob.scheduleSimple();

        DemoPeriodicJob.scheduleWithExtras("Karen");

        Timber.i("Creating our Application");
    }

}
