package mg.itu.newapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import mg.itu.newapp.dto.updaterate.UpdateRate;
import mg.itu.newapp.dto.updaterate.UpdateRateWrapper;
import mg.itu.newapp.entity.supplier.Supplier;
import mg.itu.newapp.entity.supplier.SupplierQuotation;
import mg.itu.newapp.entity.supplier.SupplierQuotationItem;
import mg.itu.newapp.services.QuotationService;
import mg.itu.newapp.utils.ApiUtils;
import mg.itu.newapp.utils.frappe.FrappeResponse;
import mg.itu.newapp.utils.frappe.FrappeResponseWrapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SupplierController {

    private ApiUtils apiUtils;
    private QuotationService quotationService;
    private ObjectMapper objectMapper;

    public SupplierController(ApiUtils apiUtils, QuotationService quotationService) {
        this.apiUtils = apiUtils;
        this.quotationService = quotationService;
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping("/suppliers")
    public String index(Model model) {

        String endPoint = "/api/method/erpnext.buying.doctype.supplier.SupplierAPI.getListSupplier";
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept", "application/json");

        ResponseEntity<String> response = apiUtils.call(
                endPoint,
                HttpMethod.GET,
                null,
                String.class,
                headers
        );

        TypeReference<FrappeResponseWrapper<List<Supplier>>> typeRef = new TypeReference<>() {};
        FrappeResponse<List<Supplier>> frappeResponse = apiUtils.bodyMessageToFrappeResponse(response, typeRef);
        model.addAttribute("suppliers", frappeResponse.getData());
        return "supplier/supplier-list";
    }

    @GetMapping("quotation/{name}")
    public String getSupplierQuotationBySupplier(@PathVariable String name,HttpSession session, Model model) {
        model.addAttribute("supplier_name", name);
        String sid = (String) session.getAttribute("FRAPPE_CookieHeader");
         List<SupplierQuotation> supplierQuotations =   quotationService.getListSupplierQuotations(sid,name);
         model.addAttribute("quotations", supplierQuotations);

        return "quotation/supplier-quotation-list";
    }

    @GetMapping("/quotation/{name}/{reference}")
    public String getSupplierQuotationDetails(@PathVariable String reference,HttpSession session,Model model) {
        String sid = (String) session.getAttribute("FRAPPE_CookieHeader");
        SupplierQuotation supplierQuotation = quotationService.getSupplierQuotation(sid,reference);
        model.addAttribute("quotation", supplierQuotation);
        return "quotation/supplier-quotation-details";
    }


    @PostMapping("/quotation/update-all-rates")
    public String updateAllRates(@ModelAttribute UpdateRateWrapper updateRateWrapper,@RequestParam("quotationName") String quotationName,
                                 @RequestParam("supplier") String supplier,HttpSession session,RedirectAttributes redirectAttributes) throws Exception {
        String sid = (String) session.getAttribute("FRAPPE_CookieHeader");
        quotationService.SubmitSupplierQuotation(updateRateWrapper,quotationName,sid);
        redirectAttributes.addFlashAttribute("message", "Rates updated successfully.");
        return "redirect:/quotation/"+supplier+"/"+quotationName;
    }
}
