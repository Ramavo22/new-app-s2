package mg.itu.newapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import mg.itu.newapp.dto.purchaseorder.PurchaseOrderDetails;
import mg.itu.newapp.entity.purchaseOrder.PurchaseOrder;
import mg.itu.newapp.services.PurchaseOrderService;
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

    private PurchaseOrderService purchaseOrderService;
    private ApiUtils apiUtils;

    public PurchaseOrderController(ApiUtils apiUtils,PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
        this.apiUtils = apiUtils;
    }


    @GetMapping("/purchase-order/{name}")
    public String index(@PathVariable String name, Model model) {
        String sid = (String) model.getAttribute("FRAPPE_CookieHeader");
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.getListPurchaseOrders(sid, name);
        model.addAttribute("purchase_orders", purchaseOrders);
        return "purchase-order/purchase-order-list";
    }

    @GetMapping("/purchase-order/{name}/{ref}")
    public String purchaseOrder(@PathVariable String name, @PathVariable String ref, Model model, HttpSession session) {
        String sid = (String) session.getAttribute("FRAPPE_CookieHeader");
        PurchaseOrderDetails purchaseOrderDetails = purchaseOrderService.getPurchaseOrderDetails(ref, sid);
        model.addAttribute("purchaseOrderDetails", purchaseOrderDetails);
        return "purchase-order/purchase-order-details";
    }
}
