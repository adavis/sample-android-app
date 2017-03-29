package info.adavis.sampleapp.jobs;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class SampleAppJobCreator implements JobCreator
{
    @Override
    public Job create(String tag)
    {
        switch (tag)
        {
            case DemoJob.JOB_TAG:
                return new DemoJob();

            case DemoPeriodicJob.JOB_TAG:
                return new DemoPeriodicJob();
        }

        return null;
    }
}
