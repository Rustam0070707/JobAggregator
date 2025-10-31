package com.RusGruz.JobAggregator.Service;

import com.RusGruz.JobAggregator.*;
import com.RusGruz.JobAggregator.Dao.JobDao;
import com.RusGruz.JobAggregator.FunInterfaces.JobScraper;
import com.RusGruz.JobAggregator.FunInterfaces.JobScraperLinkedin;
import com.RusGruz.JobAggregator.Models.JobModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapingService {
    @Autowired
    JobDao dao;


    public ResponseEntity<String> scrapAll() {

        List<JobModel> jobs = new ArrayList<>();
        List<JobScraper> scrapers = List.of( new HhUzScraper() , new RemoteScraper() );
        List<JobScraperLinkedin> scrapersConfig = List.of(new LinkedInJobScraper());
  for(JobScraper scraper : scrapers) {
jobs.addAll(scraper.scrapeJobs());
  }
  for (JobScraperLinkedin scraperConfig : scrapersConfig) {
      jobs.addAll(scraperConfig.scrapeJobs("java", "Tashkent"));
  }
  dao.saveAll(jobs);

  return ResponseEntity.ok().body(jobs.toString());
    }
}
