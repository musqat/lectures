package com.zerobase.recruitment.service;

import com.zerobase.recruitment.dto.ApplicantInfo;
import com.zerobase.recruitment.dto.ApplicationDto;
import com.zerobase.recruitment.dto.RecruitmentDto;
import com.zerobase.recruitment.entity.Application;
import com.zerobase.recruitment.entity.CompanyMember;
import com.zerobase.recruitment.entity.Education;
import com.zerobase.recruitment.entity.Recruitment;
import com.zerobase.recruitment.entity.Resume;
import com.zerobase.recruitment.enums.ApplicationStatus;
import com.zerobase.recruitment.enums.EducationLevel;
import com.zerobase.recruitment.enums.RecruitmentStatus;
import com.zerobase.recruitment.repository.ApplicationRepository;
import com.zerobase.recruitment.repository.CompanyMemberRepository;
import com.zerobase.recruitment.repository.RecruitmentRepository;
import com.zerobase.recruitment.repository.ResumeRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitmentService {

  private final RecruitmentRepository recruitmentRepository;
  private final CompanyMemberRepository companyMemberRepository;
  private final ResumeRepository resumeRepository;
  private final ApplicationRepository applicationRepository;

  @Transactional
  public void postingRecruitment(RecruitmentDto.Request request) {
    CompanyMember companyMember = companyMemberRepository.findByLoginId(
        request.companyMemberId()).orElseThrow(() -> new RuntimeException("기업 회원 정보 없음"));

    Recruitment recruitment = request.toEntity();
    recruitment.setCompanyMember(companyMember);
    recruitment.opening();

    recruitmentRepository.save(recruitment);
  }

  public List<RecruitmentDto.Response> getRecruitmentList() {
    List<Recruitment> recruitments = recruitmentRepository.findAllByStatus(RecruitmentStatus.OPEN);
    return recruitments.stream().map(Recruitment::toDto).toList();
  }

  public RecruitmentDto.Response getRecruitment(Long recruitmentId) {
    return recruitmentRepository.findById(recruitmentId)
        .orElseThrow(() -> new RuntimeException("해당하는 공고 없음")).toDto();
  }

  @Transactional
  public RecruitmentDto.Response modifyRecruitment(Long recruitmentId,
      RecruitmentDto.Request request) {
    Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
        .orElseThrow(() -> new RuntimeException("해당하는 공고 없음"));

    if (!Objects.equals(recruitment.getCompanyMember().getLoginId(),
        request.companyMemberId())) {
      throw new RuntimeException("잘못된 기업회원 정보 입니다");
    }

    return recruitment.update(request).toDto();
  }

  @Transactional
  public void deleteRecruitment(Long recruitmentId,
      RecruitmentDto.Request request) {
    Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
        .orElseThrow(() -> new RuntimeException("해당하는 공고 없음"));

    if (!Objects.equals(recruitment.getCompanyMember().getLoginId(),
        request.companyMemberId())) {
      throw new RuntimeException("잘못된 기업회원 정보 입니다");
    }
    recruitmentRepository.deleteById(recruitmentId);

  }

  @Transactional
  public void applyRecruitment(Long recruitmentId,
      ApplicationDto.Request request) {
    Resume resume = resumeRepository.findByIdAndMemberId(request.resumeId(), request.resumeId())
        .orElseThrow(() -> new RuntimeException("이력서 정보를 찾을수 없습니다."));

    Recruitment recruitment = recruitmentRepository.findByIdAndStatus(recruitmentId,
            RecruitmentStatus.OPEN)
        .orElseThrow(() -> new RuntimeException("해당하는 공고 없음"));

    Application application = Application.builder().recruitment(recruitment).resume(resume).status(
        ApplicationStatus.APPLY_FINISHED).build();

    applicationRepository.save(application);
  }

  public List<ApplicationDto.Response> getApplications(Long recruitmentId, Long companyMemberId) {
    companyMemberRepository.findById(companyMemberId)
        .orElseThrow(() -> new RuntimeException("조회 권한 없음"));

    List<Application> applicationList = applicationRepository.findAllByRecruitmentId(recruitmentId);
    return applicationList.stream().map(a -> ApplicationDto.Response.builder()
        .applicationId(a.getId())
        .status(a.getStatus())
        .appliedDate(a.getAppliedDate())
        .resumeId(a.getResume().getId())
        .resumeTitle(a.getResume().getTitle())
        .educationList(a.getResume().getEducationList())
        .name(a.getResume().getMember().getName())
        .build()).toList();

  }

  @Transactional
  public void finishedRecruitment(Long recruitmentId, Long companyMemberId) {
    Recruitment recruitment = recruitmentRepository.findByIdAndStatusAndCompanyMemberId(
            recruitmentId, RecruitmentStatus.OPEN, companyMemberId)
        .orElseThrow(() -> new RuntimeException("공고 정보 없음"));

    recruitment.closing();

    List<Application> applicationList = recruitment.getApplicationList();

    List<ApplicantInfo> applicantInfoList = applicationList.stream().map(Application::getResume)
        .map(r -> {
          Education education = r.getEducationList().stream()
              .max(Comparator.comparing(Education::getDegree))
              .orElse(Education.builder().build());
          return new ApplicantInfo(r.getId(),
              EducationLevel.findByCode(education.getCode()).getScore(education.getDegree()));
        }).sorted(Comparator.comparing(ApplicantInfo::getScore).reversed()).toList();

    Map<Long, Integer> applicantInfoMap = IntStream.range(0, applicationList.size()).boxed()
        .collect(Collectors.toMap(i -> applicantInfoList.get(i).getResumeId(), i -> i));

    applicationList.forEach(application -> {
      if (applicantInfoMap.get(application.getResume().getId()) < recruitment.getRecruiterCount()) {
        application.pass();
      } else {
        application.fail();
      }
    });
  }
}
