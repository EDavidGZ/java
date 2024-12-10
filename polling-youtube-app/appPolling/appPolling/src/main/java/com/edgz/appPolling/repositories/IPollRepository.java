package com.edgz.appPolling.repositories;

import com.edgz.appPolling.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPollRepository extends JpaRepository<Poll, Long> {
}
