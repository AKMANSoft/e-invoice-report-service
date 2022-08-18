# README #

This README would normally document whatever steps are necessary to get your application up and running.

### How do I get set up? ###

* Install Open-JDK 11, Maven 3.5.0
* Step 1: mvn clean install
* Step 2: mvn spring-boot:run
* Step 3: In any browser call : http://localhost:8080/report
* Step 4: Pdf file auto download in browser or you can find it in source code with path ../src/main/resources/pdf


If you want to generate pdf file with dynamic data input, you can using Postman call with POST method:
Import curl in postman:
curl --location --request POST 'http://localhost:8080/report' \
--header 'Content-Type: application/json' \
--data-raw '{
"sellerDetails" : {
"branchId" : "238328253359310184",
"branchCode" : "00",
"branchName" : "Mr. JOSEPH BARON KIWANUKA",
"tin" : "1012383739",
"ninBrn" : "CM86047105PN3A/",
"legalName" : "Mr. JOSEPH BARON KIWANUKA",
"businessName" : "Mr. JOSEPH BARON KIWANUKA",
"address" : "   KYENGERA WAKISO BUSIRO EAST KYENGERA TOWN COUNCIL  ",
"mobilePhone" : "123456789",
"linePhone" : "123456789",
"emailAddress" : "xyz@gmail.com",
"placeOfBusiness" : "   KYENGERA WAKISO BUSIRO EAST KYENGERA TOWN COUNCIL  ",
"referenceNo" : "05fe6ffb-e53d-4cb2-9523-01ce8c288c26"
},
"basicInformation" : {
"invoiceId" : "499082173405033470",
"invoiceNo" : "322024096072",
"antifakeCode" : "30580126073706955239",
"deviceNo" : "TCSd730332620141988",
"issuedDate" : "09/08/2022 01:08:40",
"operator" : "Admin",
"currency" : "UGX",
"currencyRate" : "0",
"invoiceType" : "1",
"invoiceKind" : "1",
"dataSource" : "106",
"invoiceIndustryCode" : "101",
"isBatch" : "0",
"isInvalid" : true,
"isRefund" : true,
"isPreview" : true,
"issuedDatePdf" : "09/08/2022 01:08:40"
},
"buyerDetails" : {
"dateFormat" : "dd/MM/yyyy",
"timeFormat" : "dd/MM/yyyy HH24:mi:ss",
"nowTime" : "2022/08/09 01:08:41",
"buyerLegalName" : "Walking Customer",
"buyerType" : "2"
},
"goodsDetails" : [ {
"item" : "B",
"itemCode" : "B",
"qty" : 1,
"unitOfMeasure" : "106",
"unitPrice" : 84.75,
"total" : 84.75,
"taxRate" : 0.18,
"tax" : 12.93,
"orderNumber" : 0,
"discountFlag" : "2",
"deemedFlag" : "2",
"exciseFlag" : "2",
"goodsCategoryId" : "42211701",
"goodsCategoryName" : "Adaptive communication switches for the physically challenged",
"exciseTax" : "0",
"vatApplicableFlag" : false
}, {
"item" : "A",
"itemCode" : "A",
"qty" : 1,
"unitOfMeasure" : "108",
"unitPrice" : 102.5,
"total" : 102.5,
"taxRate" : 0.0,
"tax" : 0.0,
"orderNumber" : 1,
"discountFlag" : "2",
"deemedFlag" : "2",
"exciseFlag" : "2",
"goodsCategoryId" : "51373309",
"goodsCategoryName" : "Acetaminophen/caffeine/dihydrocodeine",
"exciseTax" : "0",
"vatApplicableFlag" : false
} ],
"taxDetails" : [ {
"taxCategoryCode" : "01",
"taxCategory" : "A: Standard",
"netAmount" : 71.82,
"taxRate" : 0.18,
"taxAmount" : 12.93,
"grossAmount" : 84.75,
"taxRateName" : "0.18"
}, {
"taxCategoryCode" : "02",
"taxCategory" : "B: Zero",
"netAmount" : 102.5,
"taxRate" : 0.0,
"taxAmount" : 0.0,
"grossAmount" : 102.5,
"taxRateName" : "0"
} ],
"summary" : {
"netAmount" : 174.32,
"taxAmount" : 12.93,
"grossAmount" : 187.25,
"itemCount" : 2,
"modeCode" : "1",
"qrCode" : "0200000011689C3220240960720000004925000000050D38FC983CF06EA10123837390~Mr. JOSEPH BARON KIWANUKA~Walking Customer~B,A"
},
"extend" : { },
"airlineGoodsDetails" : [ ],
"payWay" : [ {
"dateFormat" : "dd/MM/yyyy",
"timeFormat" : "dd/MM/yyyy HH24:mi:ss",
"nowTime" : "2022/08/09 01:08:41",
"paymentMode" : "102",
"paymentAmount" : 84.75,
"orderNumber" : "0"
}, {
"dateFormat" : "dd/MM/yyyy",
"timeFormat" : "dd/MM/yyyy HH24:mi:ss",
"nowTime" : "2022/08/09 01:08:41",
"paymentMode" : "102",
"paymentAmount" : 102.5,
"orderNumber" : "1"
} ]
}'


