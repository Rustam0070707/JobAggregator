package com.RusGruz.JobAggregator.Service;

import com.RusGruz.JobAggregator.Dao.JobDao;
import com.RusGruz.JobAggregator.Exception.ApiRequestException;
import com.RusGruz.JobAggregator.Exception.ApiSRequestHandler;
import com.RusGruz.JobAggregator.Models.JobModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    final
    JobDao JoDao;

    public JobService(JobDao JoDao) {
        this.JoDao = JoDao;
    }

    public ResponseEntity<List<JobModel>> getAllJobs() {
return new ResponseEntity<>(JoDao.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> postAJob(JobModel mod) {
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

    public ResponseEntity<String> updateJob(JobModel mod, int id) {
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
