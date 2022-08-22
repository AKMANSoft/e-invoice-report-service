package com.example.reportservice.report.service.impl;

import com.example.reportservice.report.dto.EInvoiceReq;
import com.example.reportservice.report.exception.ApplicationException;
import com.example.reportservice.report.model.PayWay;
import com.example.reportservice.report.model.TaxDetail;
import com.example.reportservice.report.service.IReportService;
import com.example.reportservice.report.utils.DecimalToWordUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements IReportService {

    private final SpringTemplateEngine templateEngine;

    private final String TEMPLATE_PATH = "/templates/";
    private final String PDF_PATH = "src/main/resources/pdf/{file_name}";
    private final String QR_CODE = "qr_";
    private final int QR_CODE_WIDTH = 250;
    private final int QR_CODE_HEIGHT = 250;

    private final String DATE_SPACE = " ";

    @Override
    @SneakyThrows
    public Resource generate(String fileName , EInvoiceReq req) {
            log.info("[ReportServiceImpl] Start generate pdf file with fileName: {}",fileName);
            var params = buildParams(req);
            var context = new Context();
            for (var entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (entry.getKey().startsWith(QR_CODE) && !Objects.isNull(value)) {
                    value = generateQRCodeImage((String) value);
                }
                context.setVariable(key, value);
            }

            var html = loadAndFillTemplate(context);

            var byteArrayOutputStream = renderPdf(html);

            //Create pdf file
            var pdfPath = PDF_PATH.replace("{file_name}",fileName);
            try(OutputStream outputStream  = new FileOutputStream(pdfPath)){
                log.info("Writer file to: {}",pdfPath);
                byteArrayOutputStream .writeTo(outputStream);
                byteArrayOutputStream.close();
            }

            File file = ResourceUtils.getFile(pdfPath);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return resource;

    }

    private Map<String,Object> buildParams(EInvoiceReq req){
        var sellerDetail = req.getSellerDetails();
        Map<String,Object> params = new HashMap<>();
        params.put("ninBrn",sellerDetail.getNinBrn());
        params.put("tin",sellerDetail.getTin());
        params.put("legalName",sellerDetail.getLegalName());
        params.put("businessName",sellerDetail.getBusinessName());
        params.put("address",sellerDetail.getAddress());
        params.put("referenceNo",sellerDetail.getReferenceNo());
        var basicInfo = req.getBasicInformation();
        var issueDate = basicInfo.getIssuedDate();
        if(StringUtils.isNotBlank(issueDate) && issueDate.contains(DATE_SPACE)){
            params.put("basicInfoIssuedDate",issueDate.split(DATE_SPACE)[0]);
            params.put("basicInfoIssuedTime",issueDate.split(DATE_SPACE)[1]);
        }
        params.put("basicInfoOperator",basicInfo.getOperator());
        params.put("basicInfoDeviceNo",basicInfo.getDeviceNo());
        params.put("basicInfoInvoiceNo",basicInfo.getInvoiceNo());
        params.put("basicInfoAntifakeCode",basicInfo.getAntifakeCode());
        var buyerDetail = req.getBuyerDetails();
        params.put("buyerDetailsBuyerLegalName",buyerDetail.getBuyerLegalName());
        params.put("goodsDetails",req.getGoodsDetails());
        params.put("taxDetails",formatTaxDetail(req.getTaxDetails()));
        var summary = req.getSummary();
        params.put("summaryNetAmount",summary.getNetAmount());
        params.put("summaryTaxAmount",summary.getTaxAmount());
        params.put("summaryGrossAmount",summary.getGrossAmount());
        params.put("summaryGrossAmountWords", DecimalToWordUtils.getDecimalValue(summary.getGrossAmount().toString()));
        params.put("qr_web_url",summary.getQrCode());
        var payWays = req.getPayWay();
        params.put("payWays",payWays);
        params.put("payWayTotalAmountPaid",payWayTotalAmountPaid(payWays));

        if(req.getGoodsDetails() != null){
            params.put("numberOfItems",req.getGoodsDetails().size());
        }
        params.put("currency","UGX");
        params.put("mode","Online");
        return params;
    }

    private List<TaxDetail> formatTaxDetail(List<TaxDetail> taxDetails){
        return taxDetails.stream().map(t -> {
            t.taxCategoryFormat();
            return t;
        }).collect(Collectors.toList());
    }

    private BigDecimal payWayTotalAmountPaid(List<PayWay> payWays){
        if(Objects.isNull(payWays) || payWays.isEmpty()){
            return null;
        }
        BigDecimal totalAmountPaid = new BigDecimal(0);
        for (PayWay payWay : payWays) {
            totalAmountPaid = totalAmountPaid.add(payWay.getPaymentAmount());
        }
        return totalAmountPaid;
    }

    private String loadAndFillTemplate(Context context) {
        return templateEngine.process("report.html", context);
    }

    private ByteArrayOutputStream renderPdf(String html) throws IOException, DocumentException, URISyntaxException {
        var outputStream = new ByteArrayOutputStream();
        var renderer = new ITextRenderer();
        addFonts(renderer); // add font utf-8
        renderer.setDocumentFromString(html, new ClassPathResource(TEMPLATE_PATH).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        return outputStream;
    }

    private void addFonts(ITextRenderer renderer) throws URISyntaxException, IOException, DocumentException {
        var files = Files.walk(Paths.get(ClassLoader.getSystemResource("fonts").toURI())).filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());

        for (var file : files) {
            renderer.getFontResolver().addFont(file.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        }
    }

    private String generateQRCodeImage(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, QR_CODE_WIDTH, QR_CODE_HEIGHT, hints);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", bos);
        return Base64.getEncoder().encodeToString(bos.toByteArray());
    }

}
