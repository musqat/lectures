package com.zerobase.recruitment.enums;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EducationLevel {

  SEOUL_UNIV(0, "서울대학교", i -> 10 * i),
  YEONSEI_UNIV(1, "연세대학교", i -> 9 * i),
  KOREA_UNIV(2, "고려대학교", i -> 9 * i),
  KAIST_UNIV(3, "카이스트", i -> 9 * i),
  HANYANG_UNIV(4, "한양대학교", i -> 8 * i),
  SEOGANG_UNIV(5, "서강대학교", i -> 8 * i),
  SUNGYUNKWAN_UNIV(6, "성균관대학교", i -> 8 * i),
  KUNKUK_UNIV(7, "건국대학교", i -> 7 * i),
  SEJONG_UNIV(8, "세종대학교", i -> 7 * i),
  KYUNGBOOK_UNIV(9, "경북대학교", i -> 7 * i),
  SEOUL_HIGH(10, "서울고등학교", i -> 6 * i);

  private int code;
  private String name;
  private Function<Integer, Integer> score;

  public static EducationLevel findByCode(int code) {
    return Arrays.stream(EducationLevel.values()).filter(e -> Objects.equals(e.code, code))
        .findFirst().orElseThrow(() -> new RuntimeException("잘못된 코드값"));
  }

  public Integer getScore(Integer degree) {
    return score.apply(degree);
  }
}
