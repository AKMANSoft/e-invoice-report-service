package com.example.reportservice.report.dto;

import com.example.reportservice.report.model.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class EInvoiceReq {

    SellerDetail sellerDetails;

    BasicInformation basicInformation;

    BuyerDetail buyerDetails;

    List<GoodsDetail> goodsDetails;

    List<TaxDetail> taxDetails;

    Summary summary;

    List<PayWay> payWay;

}
