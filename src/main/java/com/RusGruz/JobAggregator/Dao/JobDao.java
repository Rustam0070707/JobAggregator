package com.RusGruz.JobAggregator.Dao;

import com.RusGruz.JobAggregator.Models.JobModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobDao extends JpaRepository<JobModel, Integer> {

    List<JobModel> findByTitle(String title);
}
