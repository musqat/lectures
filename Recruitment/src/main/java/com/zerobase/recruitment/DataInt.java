package com.zerobase.recruitment;

import com.zerobase.recruitment.entity.CompanyMember;
import com.zerobase.recruitment.entity.Member;
import com.zerobase.recruitment.repository.CompanyMemberRepository;
import com.zerobase.recruitment.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInt {

  private final MemberRepository MemberRepository;
  private final CompanyMemberRepository CompanyMemberRepository;

  @PostConstruct
  void init() {
    List<Member> memberList = new ArrayList<>();
    List<CompanyMember> companyMemberList = new ArrayList<>();
    IntStream.range(1, 100).forEach(i -> {
      memberList.add(Member.builder().name("개인 회원" + i).loginId("test" + i).build());
      companyMemberList.add(CompanyMember.builder().companyName("기업 회원" + i).loginId("company" + i).build());
    });

    MemberRepository.saveAll(memberList);
    CompanyMemberRepository.saveAll(companyMemberList);
  }
}
