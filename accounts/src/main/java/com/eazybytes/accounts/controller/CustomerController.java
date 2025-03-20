package com.eazybytes.accounts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.service.ICustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;

@Tag(
        name = "REST APIs for Customers in EazyBank",
        description = "REST API in EazyBank to FETCH customer details"
)
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	private final ICustomerService customersService;

	public CustomerController(ICustomerService service) {
		customersService = service;
	}

	@Operation(
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch Customer, Cards, Loans &  Account details based on a mobile number"
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
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("eazybank-correlation-id")
    		String correlationId, @RequestParam
                   @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                   String mobileNumber){
		logger.debug("fetchCustomerDetails method start");
        CustomerDetailsDto customerDetailsDto = customersService.fetchCustomerDetails(mobileNumber, correlationId);
        logger.debug("fetchCustomerDetails method end");
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
    }
}
