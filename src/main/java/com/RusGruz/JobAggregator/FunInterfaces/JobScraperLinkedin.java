package com.RusGruz.JobAggregator.FunInterfaces;

import com.RusGruz.JobAggregator.Models.JobModel;

import java.util.List;

public interface JobScraperLinkedin {
    List<JobModel> scrapeJobs(String keyword , String Location);
}
