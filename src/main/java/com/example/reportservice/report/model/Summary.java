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
public class Summary {

	BigDecimal netAmount;

	BigDecimal taxAmount;

	BigDecimal grossAmount;

	Integer itemCount;

	String modeCode;

	String qrCode;
}
