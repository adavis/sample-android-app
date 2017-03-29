package info.adavis.sampleapp.jobs;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;

import timber.log.Timber;

public class DemoPeriodicJob extends Job
{
    static final String JOB_TAG = "demo_periodic_job";

    private static final String PARAM_NAME = "param_name";

    @NonNull
    @Override
    protected Result onRunJob(Params params)
    {
        PersistableBundleCompat extras = params.getExtras();
        String name = extras.getString(PARAM_NAME, "nobody");

        Timber.i("Hello from the periodic job, %s!", name);

        return Result.SUCCESS;
    }

    public static void scheduleWithExtras (String name)
    {
        PersistableBundleCompat extras = new PersistableBundleCompat();
        extras.putString(PARAM_NAME, name);

        new JobRequest.Builder(DemoPeriodicJob.JOB_TAG)
                .setPeriodic(JobRequest.MIN_INTERVAL)
                .setExtras(extras)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                .build()
                .schedule();
    }

}
