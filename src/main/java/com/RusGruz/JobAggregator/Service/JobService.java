package com.RusGruz.JobAggregator.Service;

import com.RusGruz.JobAggregator.Repository.JobRepository;
import com.RusGruz.JobAggregator.Exception.ApiRequestException;
import com.RusGruz.JobAggregator.Models.Job;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    final
    JobRepository JoDao;

    public JobService(JobRepository JoDao) {
        this.JoDao = JoDao;
    }

    public ResponseEntity<List<Job>> getAllJobs() {
return new ResponseEntity<>(JoDao.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> postAJob(Job mod) {
        try {
            JoDao.save(mod);
        } catch (Exception e) {

            throw new ApiRequestException("Database error while saving job");
        }
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteAJob(int id ) {
       try {
           JoDao.deleteById(id);
       }
       catch (Exception e) {

           throw new ApiRequestException("Database error while saving job");
       }
        return new ResponseEntity<>("Success", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<String> updateJob(Job mod, int id) {
       mod.setId((long) id);
        try {
            JoDao.save(mod);
        }
        catch (Exception e) {

            throw new ApiRequestException("Database error while saving job");
        }

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    public ResponseEntity<String> deleteAll() {
        try {
            JoDao.deleteAll();
        }  catch (Exception e) {

            throw new ApiRequestException("Database error while saving job");
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
