package com.RusGruz.JobAggregator.Scraper;

import com.RusGruz.JobAggregator.Models.Job;

import java.util.List;

public interface JobScraperLinkedin {
    List<Job> scrapeJobs(String keyword , String Location);
}
