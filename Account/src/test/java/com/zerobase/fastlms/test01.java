package com.zerobase.fastlms;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

class test01 {

  @Test
  void test_01() {

    String value = "2021-06-08";

    DateTimeFormatter formatter = DateTimeFormatter
        .ofPattern("yyyy-MM-dd");

    try {
      LocalDate.parse(value, formatter);
    } catch (Exception e) {
    }
    return null;


  }

}
