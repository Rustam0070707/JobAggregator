package com.RusGruz.JobAggregator.Repository;

import com.RusGruz.JobAggregator.Models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    List<Job> findByTitle(String title);
}
