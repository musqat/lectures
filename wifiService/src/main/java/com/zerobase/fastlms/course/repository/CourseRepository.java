package com.zerobase.fastlms.course.repository;

import com.zerobase.fastlms.course.entity.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

  Optional<List<Course>> findByCategoryId(long categoryId);
}
