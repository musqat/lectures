package com.zerobase.recruitment.repository;

import com.zerobase.recruitment.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>{
  Optional<Member> findByLoginId(String loginId);
}