package com.RusGruz.JobAggregator.Controller;

import com.RusGruz.JobAggregator.Service.ScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("scrapingJobs")
public class ScrapingController {
    @Autowired
    ScrapingService  scrapingService;
@GetMapping("scrapAll")
    public ResponseEntity<String> scrapAll(){
    return scrapingService.scrapAll();
}
}
