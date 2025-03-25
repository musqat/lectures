package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.service.CourseService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminCourseController extends baseController {

  private final CourseService courseService;
  private final CategoryService categoryService;

  @GetMapping("/admin/course/list.do")
  public String list(Model model, CourseParam parameter) {

    parameter.init();

    List<CourseDto> courseList = courseService.list(parameter);

    long totalCount = 0;

    if (!CollectionUtils.isEmpty(courseList)) {
      totalCount = courseList.get(0).getTotalCount();
    }

    String queryString = parameter.getQueryString();
    String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(),
        queryString);

    model.addAttribute("list", courseList);
    model.addAttribute("totalCount", totalCount);
    model.addAttribute("pager", pagerHtml);

    return "admin/course/list";
  }

  @GetMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
  public String add(Model model, HttpServletRequest request, CourseInput parameter) {

    //카테고리 정보
    model.addAttribute("category", categoryService.list());

    boolean editMode = request.getRequestURI().contains("/edit.do");
    CourseDto detail = new CourseDto();

    if (editMode) {
      long id = parameter.getId();
      CourseDto existCourse = courseService.getById(id);

      if (existCourse == null) {
        // error 처리
        model.addAttribute("message", "강좌 정보가 존재하지 않습니다.");
        return "common/error";
      }
      detail = existCourse;
    }

    model.addAttribute("editMode", editMode);
    model.addAttribute("detail", detail);

    return "admin/course/add";
  }

  private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath,
      String originalFilename) {
    LocalDate now = LocalDate.now();

    String[] dirs = {
        String.format("%s/%d/", baseLocalPath, now.getYear()),
        String.format("%s/%d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue()),
        String.format("%s/%d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(),
            now.getDayOfMonth())
    };

    String urlDir = String.format("%s/%d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(),
        now.getDayOfMonth());

    for (String dir : dirs) {
      File file = new File(dir);
      if (!file.isDirectory()) {
        file.mkdirs();
      }
    }

    String fileExtension = "";
    if (originalFilename != null) {
      int dotPos = originalFilename.lastIndexOf(".");
      if (dotPos > -1) {
        fileExtension = originalFilename.substring(dotPos + 1);
      }
    }
    String uuid = UUID.randomUUID().toString().replace("-", "");
    String newFileName = String.format("%s%S", dirs[2], uuid);
    String newUrlFilename = String.format("%s%S", urlDir, uuid);
    if (fileExtension.length() > 0) {
      newFileName += "." + fileExtension;
      newUrlFilename += "." + fileExtension;
    }
    String[] returnFilename = { newFileName, newUrlFilename };

    return returnFilename;
  }

  @PostMapping(value = {"/admin/course/add.do", "admin/course/edit.do"})
  public String addSubmit(Model model, HttpServletRequest request,
      MultipartFile file, CourseInput parameter) {

    String saveFilename = "";
    String urlFilename = "";

    if (file != null) {
      String originalFilename = file.getOriginalFilename();

      String baseLocalPath = "App/Spring_Projects/fastlms/files/";
      String baseUrlPath = "/files/";

      String[] arrFileName = getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);
      saveFilename = arrFileName[0];
      urlFilename = arrFileName[1];

      try {
        File newFile = new File(saveFilename);
        FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
      } catch (IOException e) {
        log.info(e.getMessage());
      }

    }

    parameter.setFilename(saveFilename);
    parameter.setUrlFilename(urlFilename);

    boolean editMode = request.getRequestURI().contains("/edit.do");

    if (editMode) {
      long id = parameter.getId();
      CourseDto existCourse = courseService.getById(id);
      if (existCourse == null) {
        // error 처리
        model.addAttribute("message", "강좌 정보가 존재하지 않습니다.");
        return "common/error";
      }
      boolean result = courseService.set(parameter);
    } else {
      boolean result = courseService.add(parameter);
    }

    return "redirect:/admin/course/list.do";
  }

  @PostMapping(value = {"/admin/course/delete.do"})
  public String del(Model model, HttpServletRequest request, CourseInput parameter) {

    boolean result = courseService.del(parameter.getIdList());

    return "redirect:/admin/course/list.do";
  }


}
