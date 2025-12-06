package com.RusGruz.JobAggregator.Scraper;

import com.RusGruz.JobAggregator.Models.Job;

import java.util.List;
@FunctionalInterface
public interface JobScraper {
  public  List<Job> scrapeJobs();

}
