package mg.itu.newapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import mg.itu.newapp.entity.supplier.Supplier;
import mg.itu.newapp.entity.supplier.SupplierQuotation;
import mg.itu.newapp.utils.ApiUtils;
import mg.itu.newapp.utils.frappe.FrappeResponse;
import mg.itu.newapp.utils.frappe.FrappeResponseWrapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SupplierController {

    private ApiUtils apiUtils;

    public SupplierController(ApiUtils apiUtils) {
        this.apiUtils = apiUtils;
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
    public String getSupplierQuotationBySupplier(@PathVariable String name, Model model) {
        model.addAttribute("supplier_name", name);

        String endPoint = "/api/method/erpnext.buying.doctype.supplier_quotation.supplier_quotationAPI.getListSupplierQuotationBySupplier?supplier="+name;
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept", "application/json");

        ResponseEntity<String> response = apiUtils.call(
                endPoint,
                HttpMethod.GET,
                null,
                String.class,
                headers
        );
         TypeReference<FrappeResponseWrapper<List<SupplierQuotation>>> typeRef = new TypeReference<>() {};
         FrappeResponse<List<SupplierQuotation>> frappeResponse = apiUtils.bodyMessageToFrappeResponse(response, typeRef);
         model.addAttribute("quotations", frappeResponse.getData());

        return "quotation/supplier-quotation-list";
    }

    @GetMapping("/quotation/{name}/{reference}")
    public String getSupplierQuotationDetails(@PathVariable String reference,Model model) {
        String endPoint = "/api/method/erpnext.buying.doctype.supplier_quotation.supplier_quotationAPI.getAllDetailSupplierQuotation?quotation="+reference;
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept", "application/json");

        ResponseEntity<String> response = apiUtils.call(
                endPoint,
                HttpMethod.GET,
                null,
                String.class,
                headers
        );

        TypeReference<FrappeResponseWrapper<SupplierQuotation>> typeRef = new TypeReference<>() {};
        FrappeResponse<SupplierQuotation> frappeResponse = apiUtils.bodyMessageToFrappeResponse(response, typeRef);
        model.addAttribute("quotation", frappeResponse.getData());
        return "quotation/supplier-quotation-details";
    }
}
