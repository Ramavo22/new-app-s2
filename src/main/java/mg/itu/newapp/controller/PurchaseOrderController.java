package mg.itu.newapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mg.itu.newapp.entity.purchaseOrder.PurchaseOrder;
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
public class PurchaseOrderController {

    private ApiUtils apiUtils;

    public PurchaseOrderController(ApiUtils apiUtils) {
        this.apiUtils = apiUtils;
    }


    @GetMapping("/purchase-order/{name}")
    public String index(@PathVariable String name, Model model) {
        String endPoint = "/api/method/erpnext.buying.doctype.purchase_order.purchase_order_api.getListPurchaseOrderBySupplier?supplier="+name;
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        ResponseEntity<String> response = apiUtils.call(
                endPoint,
                HttpMethod.GET,
                null,
                String.class,
                headers
        );
        TypeReference<FrappeResponseWrapper<List<PurchaseOrder>>> typeRef = new TypeReference<>() {};
        FrappeResponse<List<PurchaseOrder>> frappeResponse = apiUtils.bodyMessageToFrappeResponse(response,typeRef);
        model.addAttribute("purchase_orders", frappeResponse.getData());

        return "purchase-order/purchase-order-list";
    }
}
