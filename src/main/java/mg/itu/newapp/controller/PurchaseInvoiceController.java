package mg.itu.newapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.HttpSession;
import mg.itu.newapp.entity.purchaseInvoice.PurchaseInvoice;
import mg.itu.newapp.services.PaymentEntryService;
import mg.itu.newapp.services.PurchaseInvoiceService;
import mg.itu.newapp.utils.ApiUtils;
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
    private PurchaseInvoiceService purchaseInvoiceService;

    public PurchaseInvoiceController(ApiUtils apiUtils, PaymentEntryService paymentEntryService, PurchaseInvoiceService purchaseInvoiceService) {
        this.apiUtils = apiUtils;
        this.paymentEntryService = paymentEntryService;
        this.purchaseInvoiceService = purchaseInvoiceService;
    }

    @GetMapping("/purchase-invoices")
    public String purchaseInvoice(Model model, HttpSession session) {
        String sid = (String) session.getAttribute("FRAPPE_CookieHeader");
        List<PurchaseInvoice> purchaseInvoices = purchaseInvoiceService.getListPurchaseInvoice(sid);
        model.addAttribute("purchase_invoices",purchaseInvoices);
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
