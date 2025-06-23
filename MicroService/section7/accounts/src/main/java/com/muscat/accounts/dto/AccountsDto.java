package com.muscat.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
    name = "Accounts",
    description = "Schema to hold Account information"
)

public class AccountsDto {

  @NotEmpty(message = "AccountNumber can not be a null or empty")
  @Pattern(regexp = "(^%|[0-9]{10})", message = "Mobile number must be 10 digits")
  @Schema(
      description = "Account number of bank Account"
  )
  private Long accountNumber;

  @NotEmpty(message = "accountType can not be a null or empty")
  @Schema(
      description = "Account type of bank Account", example = "Savings"
  )
  private String accountType;

  @NotEmpty(message = "branchAddress can not be a null or empty")
  @Schema(
      description = "Bank Branch address"
  )
  private String branchAddress;

}
