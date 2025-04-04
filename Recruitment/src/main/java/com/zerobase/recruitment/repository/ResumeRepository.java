package com.zerobase.recruitment.repository;

import com.zerobase.recruitment.entity.Resume;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

  Optional<Resume> findByIdAndMemberId(Long resumeId, Long memberId);
}