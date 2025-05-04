package mg.itu.newapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.HttpSession;
import mg.itu.newapp.entity.purchaseInvoice.PurchaseInvoice;
import mg.itu.newapp.services.PaymentEntryService;
import mg.itu.newapp.utils.ApiUtils;
import mg.itu.newapp.utils.frappe.FrappeResponse;
import mg.itu.newapp.utils.frappe.FrappeResponseWrapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PurchaseInvoiceController {

    private ApiUtils apiUtils;
    private PaymentEntryService paymentEntryService;

    public PurchaseInvoiceController(ApiUtils apiUtils, PaymentEntryService paymentEntryService) {
        this.apiUtils = apiUtils;
        this.paymentEntryService = paymentEntryService;
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
    @GetMapping("/purchase-invoice/{ref}/validation")
    public String payPurchaseInvoice(HttpSession session, @PathVariable String ref, RedirectAttributes redirectAttributes) {
        String sid = (String) session.getAttribute("FRAPPE_CookieHeader");
        System.out.println(sid);
        try {
            paymentEntryService.makePayment(ref, sid);
            redirectAttributes.addFlashAttribute("message", "Payment successful");
        }
        catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/purchase-invoices";
        }
        catch (Exception e) {
           redirectAttributes.addFlashAttribute("error", e.getMessage());
           return "redirect:/purchase-invoices";
        }
        return "redirect:/purchase-invoices";
    }
}
