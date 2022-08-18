package com.example.reportservice.report.service.impl;

import com.example.reportservice.report.dto.EInvoiceReq;
import com.example.reportservice.report.service.IReportService;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
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

    final String TEMPLATE_PATH = "/templates/";
    final String PDF_PATH = "src/main/resources/pdf/{file_name}";
    final String CONTENT_TYPE = "application/pdf";
    final String QR_CODE = "qr_";
    final int QR_CODE_WIDTH = 250;
    final int QR_CODE_HEIGHT = 250;

    @Override
    @SneakyThrows
    public Resource generate(String fileName , EInvoiceReq req) {
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
        params.put("basicInfoOperator",basicInfo.getOperator());
        params.put("basicInfoIssuedDate",basicInfo.getIssuedDate());
        params.put("basicInfoIssuedTime",basicInfo.getIssuedDate());
        params.put("basicInfoDeviceNo",basicInfo.getDeviceNo());
        params.put("basicInfoInvoiceNo",basicInfo.getInvoiceNo());
        params.put("basicInfoAntifakeCode",basicInfo.getAntifakeCode());
        var buyerDetail = req.getBuyerDetails();
        params.put("buyerDetailsBuyerLegalName",buyerDetail.getBuyerLegalName());
        params.put("goodsDetails",req.getGoodsDetails());
        params.put("taxDetails",req.getTaxDetails());
        var summary = req.getSummary();
        params.put("summaryNetAmount",summary.getNetAmount());
        params.put("summaryTaxAmount",summary.getTaxAmount().doubleValue());
        params.put("summaryGrossAmount",summary.getGrossAmount().doubleValue());
        params.put("qr_web_url",summary.getQrCode());
        var payWay = req.getPayWay();
        if(payWay != null && !payWay.isEmpty()){
            var firstPayWay = req.getPayWay().get(0);
            params.put("payWayPaymentAmount",firstPayWay.getPaymentAmount().doubleValue());
            params.put("payWayPaymentMode",firstPayWay.getPaymentMode());
            params.put("payWayTotalAmountPaid","100");
            params.put("numberOfItems","2");
            params.put("mode","Online");
        }
        return params;
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
