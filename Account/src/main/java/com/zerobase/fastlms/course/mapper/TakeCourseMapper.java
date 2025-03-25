package com.zerobase.fastlms.course.mapper;

import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.TakeCourseParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TakeCourseMapper {

  long selectListCount(TakeCourseParam parameter);

  List<TakeCourseDto> selectList(TakeCourseParam parameter);

  List<TakeCourseDto> selectListMyCourse(TakeCourseParam parameter);
}
