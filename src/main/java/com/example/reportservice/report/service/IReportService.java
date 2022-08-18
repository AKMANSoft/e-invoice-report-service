package com.example.reportservice.report.service;


import com.example.reportservice.report.dto.EInvoiceReq;
import org.springframework.core.io.Resource;

import java.util.Map;

public interface IReportService {

    Resource generate(String fileName, EInvoiceReq req);
}
