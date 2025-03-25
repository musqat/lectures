package com.zerobase.fastlms.admin.mapper;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

  long selectListCount(MemberParam parameter);
  List<MemberDto> selectList(MemberParam parameter);
}
