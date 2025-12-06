package com.RusGruz.JobAggregator.Service;

import com.RusGruz.JobAggregator.Repository.JobRepository;
import com.RusGruz.JobAggregator.Scraper.JobScraper;
import com.RusGruz.JobAggregator.Scraper.JobScraperLinkedin;
import com.RusGruz.JobAggregator.Models.Job;
import com.RusGruz.JobAggregator.Scraper.HhUzScraper;
import com.RusGruz.JobAggregator.Scraper.LinkedInJobScraper;
import com.RusGruz.JobAggregator.Scraper.RemoteScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapingService {
    @Autowired
    JobRepository dao;


    public ResponseEntity<String> scrapAll() {

        List<Job> jobs = new ArrayList<>();
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
