package com.victorsaez.resultapi.repositories;

import com.victorsaez.resultapi.entities.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {
    Page<Result> findAllByProfessorId(Long professorId, Pageable pageable);

}
