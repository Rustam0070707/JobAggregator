package com.RusGruz.JobAggregator.Controller;

import com.RusGruz.JobAggregator.Models.JobModel;
import com.RusGruz.JobAggregator.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("job")
public class JobController {
    @Autowired
    JobService JoService ;
    @GetMapping("GetAll")
        public ResponseEntity<List<JobModel>> getAllJobs(){
return JoService.getAllJobs();
        }
        @PostMapping("postJob")
    public ResponseEntity<String> postAJob(@RequestBody JobModel mod){
        return JoService.postAJob(mod);
        }
        @DeleteMapping("deleteJob/{id}")
    public ResponseEntity<String> deleteAJob(@PathVariable int id){
        return JoService.deleteAJob(id);

        }
        @PutMapping("update/{id}")
    public ResponseEntity<String> updateJob (@RequestBody JobModel mod, @PathVariable int id ){

        return JoService.updateJob(mod, id);
        }
        @DeleteMapping("deleteAll")
    public ResponseEntity<String> deleteAllJobs(){
        return  JoService.deleteAll();
        }

}
