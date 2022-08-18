package com.example.reportservice.report.controller;

import com.example.reportservice.report.dto.EInvoiceReq;
import com.example.reportservice.report.service.IReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final IReportService iReportService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<Resource> generateFileDefault() throws JsonProcessingException {
        String json = "{\n" +
                "  \"sellerDetails\" : {\n" +
                "    \"branchId\" : \"238328253359310184\",\n" +
                "    \"branchCode\" : \"00\",\n" +
                "    \"branchName\" : \"Mr. JOSEPH BARON KIWANUKA\",\n" +
                "    \"tin\" : \"1012383739\",\n" +
                "    \"ninBrn\" : \"CM86047105PN3A/\",\n" +
                "    \"legalName\" : \"Mr. JOSEPH BARON KIWANUKA\",\n" +
                "    \"businessName\" : \"Mr. JOSEPH BARON KIWANUKA\",\n" +
                "    \"address\" : \"   KYENGERA WAKISO BUSIRO EAST KYENGERA TOWN COUNCIL  \",\n" +
                "    \"mobilePhone\" : \"123456789\",\n" +
                "    \"linePhone\" : \"123456789\",\n" +
                "    \"emailAddress\" : \"xyz@gmail.com\",\n" +
                "    \"placeOfBusiness\" : \"   KYENGERA WAKISO BUSIRO EAST KYENGERA TOWN COUNCIL  \",\n" +
                "    \"referenceNo\" : \"05fe6ffb-e53d-4cb2-9523-01ce8c288c26\"\n" +
                "  },\n" +
                "  \"basicInformation\" : {\n" +
                "    \"invoiceId\" : \"499082173405033470\",\n" +
                "    \"invoiceNo\" : \"322024096072\",\n" +
                "    \"antifakeCode\" : \"30580126073706955239\",\n" +
                "    \"deviceNo\" : \"TCSd730332620141988\",\n" +
                "    \"issuedDate\" : \"09/08/2022 01:08:40\",\n" +
                "    \"operator\" : \"Admin\",\n" +
                "    \"currency\" : \"UGX\",\n" +
                "    \"currencyRate\" : \"0\",\n" +
                "    \"invoiceType\" : \"1\",\n" +
                "    \"invoiceKind\" : \"1\",\n" +
                "    \"dataSource\" : \"106\",\n" +
                "    \"invoiceIndustryCode\" : \"101\",\n" +
                "    \"isBatch\" : \"0\",\n" +
                "    \"isInvalid\" : true,\n" +
                "    \"isRefund\" : true,\n" +
                "    \"isPreview\" : true,\n" +
                "    \"issuedDatePdf\" : \"09/08/2022 01:08:40\"\n" +
                "  },\n" +
                "  \"buyerDetails\" : {\n" +
                "    \"dateFormat\" : \"dd/MM/yyyy\",\n" +
                "    \"timeFormat\" : \"dd/MM/yyyy HH24:mi:ss\",\n" +
                "    \"nowTime\" : \"2022/08/09 01:08:41\",\n" +
                "    \"buyerLegalName\" : \"Walking Customer\",\n" +
                "    \"buyerType\" : \"2\"\n" +
                "  },\n" +
                "  \"goodsDetails\" : [ {\n" +
                "    \"item\" : \"B\",\n" +
                "    \"itemCode\" : \"B\",\n" +
                "    \"qty\" : 1,\n" +
                "    \"unitOfMeasure\" : \"106\",\n" +
                "    \"unitPrice\" : 84.75,\n" +
                "    \"total\" : 84.75,\n" +
                "    \"taxRate\" : 0.18,\n" +
                "    \"tax\" : 12.93,\n" +
                "    \"orderNumber\" : 0,\n" +
                "    \"discountFlag\" : \"2\",\n" +
                "    \"deemedFlag\" : \"2\",\n" +
                "    \"exciseFlag\" : \"2\",\n" +
                "    \"goodsCategoryId\" : \"42211701\",\n" +
                "    \"goodsCategoryName\" : \"Adaptive communication switches for the physically challenged\",\n" +
                "    \"exciseTax\" : \"0\",\n" +
                "    \"vatApplicableFlag\" : false\n" +
                "  }, {\n" +
                "    \"item\" : \"A\",\n" +
                "    \"itemCode\" : \"A\",\n" +
                "    \"qty\" : 1,\n" +
                "    \"unitOfMeasure\" : \"108\",\n" +
                "    \"unitPrice\" : 102.5,\n" +
                "    \"total\" : 102.5,\n" +
                "    \"taxRate\" : 0.0,\n" +
                "    \"tax\" : 0.0,\n" +
                "    \"orderNumber\" : 1,\n" +
                "    \"discountFlag\" : \"2\",\n" +
                "    \"deemedFlag\" : \"2\",\n" +
                "    \"exciseFlag\" : \"2\",\n" +
                "    \"goodsCategoryId\" : \"51373309\",\n" +
                "    \"goodsCategoryName\" : \"Acetaminophen/caffeine/dihydrocodeine\",\n" +
                "    \"exciseTax\" : \"0\",\n" +
                "    \"vatApplicableFlag\" : false\n" +
                "  } ],\n" +
                "  \"taxDetails\" : [ {\n" +
                "    \"taxCategoryCode\" : \"01\",\n" +
                "    \"taxCategory\" : \"A: Standard\",\n" +
                "    \"netAmount\" : 71.82,\n" +
                "    \"taxRate\" : 0.18,\n" +
                "    \"taxAmount\" : 12.93,\n" +
                "    \"grossAmount\" : 84.75,\n" +
                "    \"taxRateName\" : \"0.18\"\n" +
                "  }, {\n" +
                "    \"taxCategoryCode\" : \"02\",\n" +
                "    \"taxCategory\" : \"B: Zero\",\n" +
                "    \"netAmount\" : 102.5,\n" +
                "    \"taxRate\" : 0.0,\n" +
                "    \"taxAmount\" : 0.0,\n" +
                "    \"grossAmount\" : 102.5,\n" +
                "    \"taxRateName\" : \"0\"\n" +
                "  } ],\n" +
                "  \"summary\" : {\n" +
                "    \"netAmount\" : 174.32,\n" +
                "    \"taxAmount\" : 12.93,\n" +
                "    \"grossAmount\" : 187.25,\n" +
                "    \"itemCount\" : 2,\n" +
                "    \"modeCode\" : \"1\",\n" +
                "    \"qrCode\" : \"0200000011689C3220240960720000004925000000050D38FC983CF06EA10123837390~Mr. JOSEPH BARON KIWANUKA~Walking Customer~B,A\"\n" +
                "  },\n" +
                "  \"extend\" : { },\n" +
                "  \"airlineGoodsDetails\" : [ ],\n" +
                "  \"payWay\" : [ {\n" +
                "    \"dateFormat\" : \"dd/MM/yyyy\",\n" +
                "    \"timeFormat\" : \"dd/MM/yyyy HH24:mi:ss\",\n" +
                "    \"nowTime\" : \"2022/08/09 01:08:41\",\n" +
                "    \"paymentMode\" : \"102\",\n" +
                "    \"paymentAmount\" : 84.75,\n" +
                "    \"orderNumber\" : \"0\"\n" +
                "  }, {\n" +
                "    \"dateFormat\" : \"dd/MM/yyyy\",\n" +
                "    \"timeFormat\" : \"dd/MM/yyyy HH24:mi:ss\",\n" +
                "    \"nowTime\" : \"2022/08/09 01:08:41\",\n" +
                "    \"paymentMode\" : \"102\",\n" +
                "    \"paymentAmount\" : 102.5,\n" +
                "    \"orderNumber\" : \"1\"\n" +
                "  } ]\n" +
                "}\n" +
                "\n";

        var req = objectMapper.readValue(json, EInvoiceReq.class);

        var fileName = UUID.randomUUID() + ".pdf";
        var resource =  iReportService.generate(fileName, req);

        MediaType mediaType = MediaTypeFactory
                .getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);

        ContentDisposition disposition = ContentDisposition
                .inline()
                .filename(fileName)
                .build();
        headers.setContentDisposition(disposition);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Resource> generateFile(@RequestBody @Validated EInvoiceReq req) {
        var fileName = UUID.randomUUID() + ".pdf";
        var resource =  iReportService.generate(fileName, req);

        MediaType mediaType = MediaTypeFactory
                .getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);

        ContentDisposition disposition = ContentDisposition
                .inline()
                .filename(fileName)
                .build();
        headers.setContentDisposition(disposition);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
