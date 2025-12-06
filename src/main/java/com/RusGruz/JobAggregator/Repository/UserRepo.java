package com.RusGruz.JobAggregator.Repository;

import com.RusGruz.JobAggregator.Models.Users;
import org.bouncycastle.crypto.examples.JPAKEExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

    Users findByUsername(String username);
}
