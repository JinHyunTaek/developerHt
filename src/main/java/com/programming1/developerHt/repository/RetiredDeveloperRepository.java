package com.programming1.developerHt.repository;

import com.programming1.developerHt.entity.Developer;
import com.programming1.developerHt.entity.RetiredDeveloper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RetiredDeveloperRepository
extends JpaRepository<RetiredDeveloper,Long> {
}
