package com.muscat.loans.controller;

import com.muscat.loans.constants.LoansConstants;
import com.muscat.loans.dto.ErrorResponseDto;
import com.muscat.loans.dto.LoansContactInfoDto;
import com.muscat.loans.dto.LoansDto;
import com.muscat.loans.dto.ResponseDto;
import com.muscat.loans.service.ILoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(
    name = "CRUD REST APIs for Loans in Bank",
    description = "CRUD REST APIs in Bank to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class LoansController {

  private static final Logger logger = LoggerFactory.getLogger(LoansController.class);

  private ILoansService iLoansService;

  public LoansController(ILoansService iLoansService) {
    this.iLoansService = iLoansService;
  }

  @Value("${build.version}")
  private String buildVersion;

  @Autowired
  private Environment environment;

  @Autowired
  private LoansContactInfoDto loansContactInfoDto;


  @Operation(
      summary = "Create Loan REST API",
      description = "REST API to create new loan inside Bank"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "201",
          description = "HTTP Status CREATED"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  }
  )
  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createLoan(@RequestParam
  @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
  String mobileNumber) {
    iLoansService.createLoan(mobileNumber);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
  }

  @Operation(
      summary = "Fetch Loan Details REST API",
      description = "REST API to fetch loan details based on a mobile number"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "HTTP Status OK"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  }
  )
  @GetMapping("/fetch")
  public ResponseEntity<LoansDto> fetchLoanDetails(
      @RequestHeader("muscathan-correlation-id") String correlationId,
      @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
      String mobileNumber) {
    logger.debug("muscathan-correlation-id found : {}", correlationId);
    LoansDto loansDto = iLoansService.fetchLoan(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(loansDto);
  }

  @Operation(
      summary = "Update Loan Details REST API",
      description = "REST API to update loan details based on a loan number"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "HTTP Status OK"
      ),
      @ApiResponse(
          responseCode = "417",
          description = "Expectation Failed"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  }
  )
  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {
    boolean isUpdated = iLoansService.updateLoan(loansDto);
    if (isUpdated) {
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
    } else {
      return ResponseEntity
          .status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
    }
  }

  @Operation(
      summary = "Delete Loan Details REST API",
      description = "REST API to delete Loan details based on a mobile number"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "HTTP Status OK"
      ),
      @ApiResponse(
          responseCode = "417",
          description = "Expectation Failed"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  }
  )
  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam
  @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
  String mobileNumber) {
    boolean isDeleted = iLoansService.deleteLoan(mobileNumber);
    if (isDeleted) {
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
    } else {
      return ResponseEntity
          .status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
    }
  }

  @Operation(
      summary = "Get Build Information",
      description = "Get Build Information that is deployed into loans microservice"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "HTTP Status OK"

      ), @ApiResponse(
      responseCode = "500",
      description = "Http Status Internal Server Error",
      content = @Content(
          schema = @Schema(implementation = ErrorResponseDto.class)
      )
  )
  })
  @GetMapping("/build-info")
  public ResponseEntity<String> getBuildInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
  }

  @Operation(
      summary = "Get Java Version",
      description = "Get Java Version that is deployed into loans microservice"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "HTTP Status OK"

      ), @ApiResponse(
      responseCode = "500",
      description = "Http Status Internal Server Error",
      content = @Content(
          schema = @Schema(implementation = ErrorResponseDto.class)
      )
  )
  })
  @GetMapping("/java-version")
  public ResponseEntity<String> getJavaVersion() {
    return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
  }

  @Operation(
      summary = "Get Contact Info",
      description = "Contact Info details that can be reached out in case of any issues"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "HTTP Status OK"

      ), @ApiResponse(
      responseCode = "500",
      description = "Http Status Internal Server Error",
      content = @Content(
          schema = @Schema(implementation = ErrorResponseDto.class)
      )
  )
  })
  @GetMapping("/contact-info")
  public ResponseEntity<LoansContactInfoDto> getContactInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(loansContactInfoDto);
  }


}