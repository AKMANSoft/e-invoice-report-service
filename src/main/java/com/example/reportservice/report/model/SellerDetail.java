package com.example.reportservice.report.model;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SellerDetail {

	String branchId;
	
	String branchCode;
	
	String branchName;
	
	String tin;
	
	String ninBrn;
	
	String legalName;
	
	String businessName;

	String address;

	String mobilePhone;

	String linePhone;

	String emailAddress;

	String placeOfBusiness;

	String referenceNo;

}
