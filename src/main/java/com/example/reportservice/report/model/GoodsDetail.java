package com.example.reportservice.report.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodsDetail {

	String item;
	
	String itemCode;
	
	Integer qty;
	
	String unitOfMeasure;

	BigDecimal unitPrice;

	BigDecimal total;

	BigDecimal taxRate;

	BigDecimal tax;

	Integer orderNumber;

	String discountFlag;

	String deemedFlag;

	String exciseFlag;

	String goodsCategoryId;

	String goodsCategoryName;

	String exciseTax;

	Boolean vatApplicableFlag;
}
