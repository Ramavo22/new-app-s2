package mg.itu.newapp.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mg.itu.newapp.dto.purchaseorder.PurchaseOrderDetails;
import mg.itu.newapp.entity.purchaseOrder.PurchaseOrder;
import mg.itu.newapp.utils.ApiUtils;
import mg.itu.newapp.utils.frappe.FrappeResponse;
import mg.itu.newapp.utils.frappe.FrappeResponseWrapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PurchaseOrderService {
    private ApiUtils apiUtils;
    private ObjectMapper objectMapper;

    public PurchaseOrderService(ApiUtils apiUtils, ObjectMapper objectMapper) {
        this.apiUtils = apiUtils;
        this.objectMapper = objectMapper;
    }


    public List<PurchaseOrder> getListPurchaseOrders(String sid,String name)  {
        String endPoint = "/api/method/erpnext.buying.doctype.purchase_order.purchase_order_api.getListPurchaseOrderBySupplier?supplier="+name;
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Cookie",sid);
        ResponseEntity<String> response = apiUtils.call(
                endPoint,
                HttpMethod.GET,
                null,
                String.class,
                headers
        );
        TypeReference<FrappeResponseWrapper<List<PurchaseOrder>>> typeRef = new TypeReference<>() {};
        FrappeResponse<List<PurchaseOrder>> frappeResponse = apiUtils.bodyMessageToFrappeResponse(response,typeRef);
        return frappeResponse.getData();
    }

    public PurchaseOrderDetails getPurchaseOrderDetails(String refName,String sid) {
        String endPoint = "/api/method/erpnext.buying.doctype.purchase_order.purchase_order_api.get_purchase_order_details?po_name="+refName;
        Map<String,String> headers = new HashMap<>();
        headers.put("Cookie",sid);
        headers.put("Accept","application/json");
        headers.put("Content-Type","application/json");

        ResponseEntity<String> response = apiUtils.call(
                endPoint,
                HttpMethod.GET,
                null,
                String.class,
                headers
        );

        TypeReference<FrappeResponseWrapper<PurchaseOrderDetails>> typeRef = new TypeReference<>() {};
        FrappeResponse<PurchaseOrderDetails> frappeResponse = apiUtils.bodyMessageToFrappeResponse(response, typeRef);
        return frappeResponse.getData();

    }

}
