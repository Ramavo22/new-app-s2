package mg.itu.newapp.services;

import com.fasterxml.jackson.core.type.TypeReference;
import mg.itu.newapp.entity.purchaseInvoice.PurchaseInvoice;
import mg.itu.newapp.utils.ApiUtils;
import mg.itu.newapp.utils.frappe.FrappeResponse;
import mg.itu.newapp.utils.frappe.FrappeResponseWrapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PurchaseInvoiceService {

    private ApiUtils apiUtils;

    public PurchaseInvoiceService(ApiUtils apiUtils) {
        this.apiUtils = apiUtils;
    }

    public PurchaseInvoice getPurchaseInvoice(String name,String FrappeCookie) throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Cookie", FrappeCookie);

        String endPoint ="http://evals2.com:8000/api/method/erpnext.accounts.doctype.purchase_invoice.purchase_invoice_api.getListPurchaseInvoiceByName?name="+name;
        ResponseEntity<String> response = apiUtils.call(
                endPoint,
                HttpMethod.GET,
                null,
                String.class,
                headers
        );

        TypeReference<FrappeResponseWrapper<PurchaseInvoice>> typeRef = new TypeReference<>() {};
        FrappeResponse<PurchaseInvoice> frappeResponse = apiUtils.bodyMessageToFrappeResponse(response, typeRef);
        return frappeResponse.getData();
    }
}
