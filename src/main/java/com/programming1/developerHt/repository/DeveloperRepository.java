package com.programming1.developerHt.repository;

import com.programming1.developerHt.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface DeveloperRepository
        extends JpaRepository<Developer,Long> {
    Optional<Developer> findByMemberId(String memberId);
}
