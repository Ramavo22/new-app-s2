package mg.itu.newapp.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mg.itu.newapp.entity.payment.PaymentEntry;
import mg.itu.newapp.entity.purchaseInvoice.PurchaseInvoice;
import mg.itu.newapp.utils.ApiUtils;
import mg.itu.newapp.utils.frappe.FrappeResponse;
import mg.itu.newapp.utils.frappe.FrappeResponseWrapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Map;

@Service
public class PaymentEntryService {

    private PurchaseInvoiceService purchaseInvoiceService;
    private ApiUtils apiUtils;
    private ObjectMapper objectMapper;

    public PaymentEntryService(PurchaseInvoiceService purchaseInvoiceService, ApiUtils apiUtils) {
        this.purchaseInvoiceService = purchaseInvoiceService;
        this.apiUtils = apiUtils;
        objectMapper = new ObjectMapper();
    }

    public void makePayment(String name,String sid)throws Exception{

        System.out.println("makePayment");
        PurchaseInvoice purchaseInvoice = purchaseInvoiceService.getPurchaseInvoice(name,sid);
        PaymentEntry paymentEntry = new PaymentEntry(purchaseInvoice);
        String paymentEntryJson = objectMapper.writeValueAsString(paymentEntry);
        Map<String, String> headersMap = apiUtils.getHeadersMap(sid);
        String endPoint = "/api/resource/Payment Entry";
        ResponseEntity<String> response = apiUtils.call(
            endPoint,
            HttpMethod.POST,
            paymentEntryJson,
            String.class,
            headersMap
        );
        if(response.getStatusCode() == HttpStatus.OK){
            JsonNode root = objectMapper.readTree(response.getBody());
            String ref = root.path("data").path("name").asText();
            String endPointForValidation = "/api/resource/Payment Entry/"+ref+"?run_method=submit";
            response =  apiUtils.call(
                    endPointForValidation,
                    HttpMethod.POST,
                    null,
                    String.class,
                    headersMap
            );
            System.out.println(response.getStatusCode());
        }
        if(response.getStatusCode() == HttpStatus.EXPECTATION_FAILED){
            throw new RuntimeException("Payment entry could not be made, Already created for " + name);
        }
    }

}

