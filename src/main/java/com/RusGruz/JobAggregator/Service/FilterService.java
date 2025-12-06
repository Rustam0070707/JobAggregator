package com.RusGruz.JobAggregator.Service;

import com.RusGruz.JobAggregator.Repository.JobRepository;
import com.RusGruz.JobAggregator.Models.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilterService {
   @Autowired
   JobRepository jobRepository;



    public ResponseEntity<List<Job>> SearchByTitle(String title) {
        List<Job> list = new ArrayList<>();
        List<Job> jobs = jobRepository.findAll();
        if (jobs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (Job job : jobs) {
            if(job.getTitle().contains(title)) {
                list.add(job);
            }
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    public ResponseEntity<List<Job>> searchByCompany(String company) {
        List<Job> list = new ArrayList<>();
        List<Job> jobs = jobRepository.findAll();
        if (jobs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (Job job : jobs) {
            if(job.getTitle().contains(company)) {
                list.add(job);
            }
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    public ResponseEntity<List<Job>> searchByCompanyAndTitle(String title, String company) {
        List<Job> list = new ArrayList<>();
        List<Job> jobs = jobRepository.findAll();
        if (jobs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (Job job : jobs) {
            if(job.getTitle().contains(title)&& job.getCompany().contains(company)) {
                list.add(job);
            }
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
