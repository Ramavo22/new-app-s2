package mg.itu.newapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import mg.itu.newapp.entity.purchaseInvoice.PurchaseInvoice;
import mg.itu.newapp.utils.ApiUtils;
import mg.itu.newapp.utils.frappe.FrappeResponse;
import mg.itu.newapp.utils.frappe.FrappeResponseWrapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PurchaseInvoiceController {

    private ApiUtils apiUtils;

    public PurchaseInvoiceController(ApiUtils apiUtils) {
        this.apiUtils = apiUtils;
    }

    @GetMapping("/purchase-invoices")
    public String purchaseInvoice(Model model) {
        String endPoint = "/api/method/erpnext.accounts.doctype.purchase_invoice.purchase_invoice_api.getListPurchaseInvoice";
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");

        ResponseEntity<String> response = apiUtils.call(
                endPoint,
                HttpMethod.GET,
                null,
                String.class,
                headers
        );

        TypeReference<FrappeResponseWrapper<List<PurchaseInvoice>>> typeRef = new TypeReference<>() {};
        FrappeResponse<List<PurchaseInvoice>> frappeResponse = apiUtils.bodyMessageToFrappeResponse(response,typeRef);
        model.addAttribute("purchase_invoices", frappeResponse.getData());
        return "purchase-invoice/purchase-invoice-list";
    }
}
