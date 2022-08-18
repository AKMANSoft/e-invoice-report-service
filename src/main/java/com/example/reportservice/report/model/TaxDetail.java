package com.example.reportservice.report.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaxDetail {

	String taxCategoryCode;
	
	String taxCategory;

	BigDecimal netAmount;

	BigDecimal taxRate;

	BigDecimal taxAmount;

	BigDecimal grossAmount;

	String taxRateName;

	String taxCategoryFormat;

	public void taxCategoryFormat(){
		if(Objects.nonNull(this.taxRate)){
			taxCategoryFormat = String.format("%s (%s)",taxCategory, new DecimalFormat("0.#").format(taxRate.doubleValue() * 100)+"%");
		}
	}
}
