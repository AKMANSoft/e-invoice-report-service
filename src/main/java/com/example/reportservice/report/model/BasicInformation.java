package com.example.reportservice.report.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BasicInformation {

	String invoiceId;
	
	String invoiceNo;
	
	String antifakeCode;
	
	String deviceNo;
	
	String issuedDate;
	
	String operator;
	
	String currency;

	String currencyRate;

	String invoiceType;

	String invoiceKind;

	String dataSource;

	String invoiceIndustryCode;

	String isBatch;

	@JsonProperty("isInvalid")
	Boolean isInvalid;

	@JsonProperty("isRefund")
	Boolean isRefund;

	@JsonProperty("isPreview")
	Boolean isPreview;

	String issuedDatePdf;
}
