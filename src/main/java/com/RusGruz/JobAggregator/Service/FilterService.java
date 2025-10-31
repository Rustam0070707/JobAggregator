package com.RusGruz.JobAggregator.Service;

import com.RusGruz.JobAggregator.Dao.JobDao;
import com.RusGruz.JobAggregator.Models.JobModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilterService {
   @Autowired
    JobDao jobDao;



    public ResponseEntity<List<JobModel>> SearchByTitle(String title) {
        List<JobModel> list = new ArrayList<>();
        List<JobModel> jobModels = jobDao.findAll();
        if (jobModels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (JobModel jobModel : jobModels) {
            if(jobModel.getTitle().contains(title)) {
                list.add(jobModel);
            }
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    public ResponseEntity<List<JobModel>> searchByCompany(String company) {
        List<JobModel> list = new ArrayList<>();
        List<JobModel> jobModels = jobDao.findAll();
        if (jobModels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (JobModel jobModel : jobModels) {
            if(jobModel.getTitle().contains(company)) {
                list.add(jobModel);
            }
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    public ResponseEntity<List<JobModel>> searchByCompanyAndTitle(String title, String company) {
        List<JobModel> list = new ArrayList<>();
        List<JobModel> jobModels = jobDao.findAll();
        if (jobModels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (JobModel jobModel : jobModels) {
            if(jobModel.getTitle().contains(title)&&jobModel.getCompany().contains(company)) {
                list.add(jobModel);
            }
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
