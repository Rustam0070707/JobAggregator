package com.RusGruz.JobAggregator.FunInterfaces;

import com.RusGruz.JobAggregator.Models.JobModel;

import java.util.List;
@FunctionalInterface
public interface JobScraper {
  public  List<JobModel> scrapeJobs();

}
