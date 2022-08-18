package com.example.reportservice.report.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BuyerDetail {

	String dateFormat;
	
	String timeFormat;
	
	String nowTime;
	
	String buyerLegalName;
	
	String buyerType;

}
