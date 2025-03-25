package com.zerobase.fastlms.course.repository;

import com.zerobase.fastlms.course.entity.TakeCourse;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TakeCourseRepository extends JpaRepository<TakeCourse, Long> {

  long countByCourseIdAndUserIdAndStatusIn(Long courseId, String userId, Collection<String> statusList);
}
