package com.muscat.accounts.controller;

import com.muscat.accounts.constants.AccountConstants;
import com.muscat.accounts.dto.CustomerDto;
import com.muscat.accounts.dto.ErrorResponseDto;
import com.muscat.accounts.dto.ResponseDto;
import com.muscat.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
    name = "CRUD REST API for Accounts in Bank",
    description = "CRUD REST API for in bank to CREATE, UPDATE, FETCH and DELETE accounts details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor @Validated
public class AccountsController {

  private IAccountService iAccountService;

  @Operation(
      summary = "Create Accounts REST API",
      description = "REST API to create new Customer & Account inside Bank"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "201",
          description = "HTTP Status OK"

      ), @ApiResponse(
      responseCode = "500",
      description = "Http Status Internal Server Error",
      content = @Content(
          schema = @Schema(implementation = ErrorResponseDto.class)
      )
  )
  })
  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
    iAccountService.createAccount(customerDto);
    return ResponseEntity.
        status(HttpStatus.CREATED)
        .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
  }

  @Operation(
      summary = "Fetch Accounts Details REST API",
      description = "REST API to fetch Customer & Account details based on a mobile number"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "HTTP Status OK"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Http Status Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  })
  @GetMapping("/fetch")
  public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
  @Pattern(regexp = "(^%|[0-9]{10})", message = "Mobile number must be 10 digits")
  String mobileNumber) {
    CustomerDto customerDto = iAccountService.fetchAccount(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(customerDto);
  }

  @Operation(
      summary = "Update Accounts Details REST API",
      description = "REST API to update Customer & Account details based on a mobile number"
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
          description = "Http Status Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  })
  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateAccountDetails(
      @Valid @RequestBody CustomerDto customerDto) {
    boolean isUpdated = iAccountService.updateAccount(customerDto);
    if (isUpdated) {
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
    } else {
      return ResponseEntity
          .status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
    }

  }

  @Operation(
      summary = "Delete Accounts Details REST API",
      description = "REST API to delete Customer & Account details based on a mobile number"
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
          description = "Http Status Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )

  })
  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
  @Pattern(regexp = "(^%|[0-9]{10})", message = "Mobile number must be 10 digits")
  String mobileNumber) {
    boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
    if (isDeleted) {
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
    } else {
      return ResponseEntity
          .status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
    }
  }
}
