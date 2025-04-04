package com.zerobase.recruitment.repository;

import com.zerobase.recruitment.entity.Application;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
  List<Application> findAllByRecruitmentId(Long recruitmentId);
}
