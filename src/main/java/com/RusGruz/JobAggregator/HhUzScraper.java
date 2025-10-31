package com.RusGruz.JobAggregator;

import com.RusGruz.JobAggregator.FunInterfaces.JobScraper;
import com.RusGruz.JobAggregator.Models.JobModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class HhUzScraper implements JobScraper {
    private static final String API_URL  = "https://api.hh.uz/vacancies?area=2758&text=Java&per_page=20";

    @Override
    public List<JobModel> scrapeJobs() {
        List<JobModel> jobs = new ArrayList<>();
        RestTemplate restTemplate  = new RestTemplate();

        try{
            String response = restTemplate.getForObject(API_URL, String.class);
            JSONObject json = new JSONObject(response);
            JSONArray items = json.getJSONArray("items");

            for(int i = 0; i < items.length(); i++){
                JSONObject item  = items.getJSONObject(i);

                JobModel job = new JobModel();
                job.setTitle(item.getString("name"));
                job.setCompany(item.getJSONObject("employer").getString("name"));
                job.setUrl(item.getString("alternate_url"));
                job.setSource("Hh.uz");
                job.setLocation(item.getJSONObject("area").getString("name"));
                job.setPostDate(LocalDate.parse((item.getString("published_at")).substring(0,10)));
                jobs.add(job);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
return jobs;
    }
}
