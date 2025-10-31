package com.RusGruz.JobAggregator.Controller;

import com.RusGruz.JobAggregator.Dao.JobDao;
import com.RusGruz.JobAggregator.Models.JobModel;
import com.RusGruz.JobAggregator.Service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("filter")
public class FilterController {

    @Autowired
    FilterService filterService;
    @GetMapping("byTitle/{title}")
    public ResponseEntity<List<JobModel>> SearchByTitle (@PathVariable String title){
return filterService.SearchByTitle(title);
    }
    @GetMapping("byCompany/{company}")
    public ResponseEntity<List<JobModel>> SearchByCompany (@PathVariable String company){
        return filterService.searchByCompany(company);
    }
    @GetMapping("byCompany&Title/{title}/{company}")
    public ResponseEntity<List<JobModel>> SearchByCompanyAndTitle (@PathVariable String title, @PathVariable String company){
        return filterService.searchByCompanyAndTitle(title,company);
    }

}
